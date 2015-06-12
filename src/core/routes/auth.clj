(ns core.routes.auth
  (:require [compojure.core :refer [defroutes GET POST]]
            [noir.session :as session]
            [noir.response :as resp]
            [noir.validation :as vali]
            [noir.util.crypt :as crypt]
            [core.models.user :as user]
            [noir.util.route :refer [restricted]]
            [core.routes.home :refer [home]]))

;;
;; Registration methods
;;

(defn format-error
  "Formats possible postgresql errors for human readable output"
  [email ex]
  (cond
    (and (instance? org.postgresql.util.PSQLException ex)
         (= 0 (.getErrorCode ex)))
    (str "A user with the email " email " already exists!")
    :else "An error has occured while processing the request"))

(defn valid-user?
  "Validates a user-spec to increase the likelihood of being successfully processed by
  the postgres database"
  [{fname :first_name lname :last_name email :email pass1 :password pass2 :password2}]
  (vali/rule (vali/has-value? fname)
             [:firstname "A first name is required"])
  (vali/rule (vali/has-value? lname)
             [:lastname "A last name is required"])
  (vali/rule (vali/has-value? email)
             [:email "An email is required"])
  (vali/rule (vali/is-email? email)
             [:email "A valid email is required"])
  (vali/rule (vali/min-length? pass1 3)
             [:password "Password must be atleast 3 characters"])
  (vali/rule (= pass1 pass2)
             [:password "Passwords do not match"])
  (not (vali/errors? :firstname :lastname :email :password)))

(defn first-vali-error [fields]
  (if (empty? fields)
    nil
    (if-let [v (vali/get-errors (peek fields))]
      (first v)
      (recur (pop fields)))))

(defn handle-registration
  [firstname lastname email pass1 pass2]
  (let [user-spec {:first_name firstname
                   :last_name lastname
                   :email email
                   :password pass1
                   :password2 pass2}]
    (if (valid-user? user-spec)
      (try
        (user/create-user-and-encrypt-pass (dissoc user-spec :password2))
        (session/put! :user ((user/get-user-by-email email) :id))
        (resp/redirect "/")
        (catch Exception ex
          (vali/rule false [:email (format-error email ex)])
          (home {:error "A user already exist with that email address"})))
      (home {:error (first-vali-error [:firstname :lastname :email :password])}))))


;;
;; Login and Logout methods
;;

(defn handle-logout []
  (session/clear!)
  (resp/redirect "/"))

(defn handle-login [email pass]
  (let [user (first (user/get-user-by-email email))]
    (if (and user
             (crypt/compare pass (:password user)))
      (session/put! :user (:id user))))
  (resp/redirect "/"))

(defroutes
  auth-routes

  (POST "/register" [firstname lastname email pass1 pass2]
    (handle-registration firstname lastname email pass1 pass2))

  (POST "/login" [email pass]
    (handle-login email pass))

  (GET "/logout" []
    (handle-logout)))