;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/

{{=<< >>=}}
(ns <<namespace>>.client
  (:require [ajax.core :as ajax]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [reagent.core :as reagent]
            [<<namespace>>.client.home :as home]<<#hydrogen-session?>>
            [<<namespace>>.client.landing :as landing]<</hydrogen-session?>>
            [<<namespace>>.client.routes :as routes]<<#hydrogen-session-keycloak?>>
            [<<namespace>>.client.session :as session]<</hydrogen-session-keycloak?>>
            [<<namespace>>.client.theme :as theme]
            [<<namespace>>.client.tooltip :as tooltip]
            [<<namespace>>.client.tooltip.generic-popup :as tooltip.generic-popup]
            [<<namespace>>.client.tooltip.loading-popup :as tooltip.loading-popup]
            [<<namespace>>.client.util :as util]
            [<<namespace>>.client.view :as view]))

(def default-db
  {:theme :light})<<#hydrogen-session-cognito?>>

(rf/reg-event-db
 ::set-config
 (fn [db [_ config]]
   (assoc db :config config)))<</hydrogen-session-cognito?>><<#hydrogen-session-keycloak?>>

(rf/reg-event-fx
  ::set-config
  (fn [{:keys [db]} [_ config]]
    (merge
      {:db (assoc db :config config)}
      (when (session/keycloak-process-ongoing?)
        {:init-and-authenticate config}))))<</hydrogen-session-keycloak?>>

(rf/reg-event-fx
 ::load-app
 (fn [{:keys [db]} [_]]
   {:db default-db<<#hydrogen-session?>>
    :http-xhrio {:method :get
                 :uri "/api/config"
                 :format (ajax/json-request-format)
                 :response-format (ajax/transit-response-format)
                 :on-success [::set-config]
                 :on-failure [::util/generic-error]}<</hydrogen-session?>>}))

(defn app []
  (let [theme (rf/subscribe [::theme/get-theme])
        active-view (rf/subscribe [::view/active-view])]
    (fn []
      [:div.app-container
       {:on-click #(tooltip/destroy-on-click-out (.. % -target))
        :class (str "theme-" (name @theme))}
       [:div.app-container__main
        {:id "app-container__main"}
        (case @active-view
          :foo [:div "FIXME"]
          [home/main])]
       [tooltip.loading-popup/main]
       [tooltip.generic-popup/main]])))

(defn main []<<#hydrogen-session?>>
  (let [active-view (rf/subscribe [::view/active-view])]
    (fn []
      (if (= @active-view :landing)
        [landing/main]
        [app])))<</hydrogen-session?>><<^hydrogen-session?>>
  [app]<</hydrogen-session?>>)

(defn dev-setup []
  (when goog.DEBUG
    (enable-console-print!)
    (println "Dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [main] (.getElementById js/document "app")))

(defn ^:export init []
  (dev-setup)
  (rf/dispatch-sync [::load-app])<<#hydrogen-session-keycloak?>>
  (view/fix-query-params js/location.hash)<</hydrogen-session-keycloak?>>
  (routes/app-routes)
  (mount-root))
