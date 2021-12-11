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

(deftest igual?-test
  (testing "igual?"
    (is (true? (igual? "a" "a")))
    (is (true? (igual? "A" "a")))
    (is (true? (igual? "5" 5)))
    (is (true? (igual? 5 "5")))
    (is (true? (igual? "a" "A")))
    (is (true? (igual? "" "")))
    (is (true? (igual? "longer string   "  "longer string   ")))
    (is (false? (igual? "a" "b")))))

(deftest buscar-test
  (testing "buscar"
    (is (= 3 (buscar 'c '(c 3))))
    (is (= 3 (buscar 'c '(a 1 b 2 c 3 d 4 e 5))))
    (is (= (generar-mensaje-error :unbound-variable 'c) (buscar 'c '())))
    (is (= (generar-mensaje-error :unbound-variable 'c) (buscar 'c '(a 1 b 2 d 4 e 5))))))

(deftest error?-test
  (testing "error?"
    (is (false? (error? (list))))
    (is (false? (error? (list 'not 'an 'error))))
    (is (true? (error? (list (symbol ";WARNING:") 'an 'error))))
    (is (true? (error? (list (symbol ";ERROR:") 'an 'error))))))

(deftest proteger-bool-en-str-test
  (testing "proteger-bool-en-str"
    (is (= "" (proteger-bool-en-str "")))
    (is (= "asdf" (proteger-bool-en-str "asdf")))
    (is (= "as%tdf" (proteger-bool-en-str "as#tdf")))
    (is (= "as%tdf" (proteger-bool-en-str "as%tdf")))))

(deftest restaurar-bool-test
  (testing "restaurar-bool"
    (is (= "" (restaurar-bool "")))
    (is (= "asdf" (restaurar-bool "asdf")))
    (is (= "as#tdf" (restaurar-bool "as#tdf")))
    (is (= "as#tdf" (restaurar-bool "as%tdf")))))

(deftest fnc-append-test
  (testing "fnc-append"
    (is (= '(1 2 3 4 5 6 7) (fnc-append '( (1 2) (3) (4 5) (6 7)))))
    (is (= '(1 2 4 5 6 7) (fnc-append '( (1 2) () (4 5) (6 7)))))
    (is (= (generar-mensaje-error :wrong-type-arg 'append 'A) (fnc-append '( (1 2) A (4 5) (6 7)))))
    (is (= (generar-mensaje-error :wrong-type-arg 'append nil) (fnc-append '( (1 2) nil (4 5) (6 7)))))))

(deftest fnc-equal-test
  (testing "fnc-append"
    (is (= "#t" (fnc-equal? '())))
    (is (= "#t" (fnc-equal? '(1 1))))
    (is (= "#t" (fnc-equal? '("asd" "aSd" "asd"))))
    (is (= "#t" (fnc-equal? '(\a \a \A))))
    (is (= "#t" (fnc-equal? '('a 'a 'a))))
    (is (= "#t" (fnc-equal? '("asd" "aSd" "asd" "ASD" "Asd"))))
    (is (= "#f" (fnc-equal? '(1 2 1))))
    (is (= "#f" (fnc-equal? '("asd" "aSd" "other" "ASD" "Asd"))))
    (is (= "#f" (fnc-equal? '('x 'a 'a))))
    (is (= "#f" (fnc-equal? '(\b \a \A))))))
