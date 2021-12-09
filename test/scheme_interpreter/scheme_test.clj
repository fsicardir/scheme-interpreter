(ns scheme-interpreter.scheme-test
  (:require [clojure.test :refer :all]
            [scheme-interpreter.scheme :refer :all]))

(deftest verificar-parentesis-test
  (testing "verificar-parentesis"
    (is (= 0 (verificar-parentesis "")))
    (is (= 0 (verificar-parentesis "asdf")))
    (is (= 1 (verificar-parentesis "asd(f")))
    (is (= 2 (verificar-parentesis "a(asd(f")))
    (is (= 2 (verificar-parentesis "a(a)sd(f(")))
    (is (= 0 (verificar-parentesis "a(a)sd(f)")))
    (is (= -1 (verificar-parentesis "a)sdf")))
    (is (= -1 (verificar-parentesis "a(a)sdf)")))
    (is (= 1 (verificar-parentesis "(hola 'mundo")))
    (is (= -1 (verificar-parentesis "(hola '(mundo)))")))
    (is (= -1 (verificar-parentesis "(hola '(mundo) () 6) 7)")))
    (is (= -1 (verificar-parentesis "(hola '(mundo) () 6) 7) 9)")))
    (is (= 0 (verificar-parentesis "(hola '(mundo) )")))))
