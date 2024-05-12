(ns scratch)


(let [hours {:oct7 7
      :oct14 3}]
  [:table nil
    [:tr '([:th :Week] [:th :Hours])]
    (first (map (fn [x] [:tr (map #(vec [:td %]) x)]) hours))])
; [:table
;  nil
;  [:tr ([:th :Week] [:th :Hours])]
;  [:tr ([:td :oct7] [:td 7])]]



(vec [1 2 3])
(conj [:tbody] [:tr '([:td 1][:td 2])])

(conj [1] [2 3])




