;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client.home
  (:require [re-frame.core :as rf]<<#hydrogen-session?>>
            [<<namespace>>.client.session :as session]
            [<<namespace>>.client.user :as user]<</hydrogen-session?>>
            [<<namespace>>.client.view :as view]))

(rf/reg-event-fx
 ::go-to-home
 (fn [_ _]
   {:dispatch [::view/set-active-view :home]
    :redirect "/#/home"}))<<#hydrogen-session?>>

(defn- user-details []
  (let [user-data (rf/subscribe [::user/user-data])]
    (fn []
      (when @user-data
        [:div {:style {:text-align :center}}
         [:p (str "Hello again " (:first-name @user-data) " " (:last-name @user-data) "!")]
         [:img {:src (or (:avatar @user-data) "images/user.svg")
                :style {:width "100px" :height "100px" :border-radius "50%"}}]]))))

(defn- logout []
  [:div.logout
   {:on-click #(do (rf/dispatch [::session/user-logout])
                   (view/redirect! "/#/landing"))}
   "Logout"])<</hydrogen-session?>>

(defn main []
  [:div {:id "home"}
   [:img {:src "images/hydrogen-logo-white.svg" :alt "Hydrogen logo"}]
   [:h1 "Welcome to Hydrogen!"]<<#hydrogen-session?>>
   [user-details]
   [logout]<</hydrogen-session?>>])
