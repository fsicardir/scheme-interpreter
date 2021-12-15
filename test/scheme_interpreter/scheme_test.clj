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
    (is (= 3 (buscar 'C '(a 1 b 2 c 3 d 4 e 5))))
    (is (= (generar-mensaje-error :unbound-variable 'c) (buscar 'c '())))
    (is (= (generar-mensaje-error :unbound-variable 'c) (buscar 'c '(a 1 b 2 d 4 e 5))))))

(deftest error?-test
  (testing "error?"
    (is (false? (error? (list))))
    (is (false? (error? 123)))
    (is (false? (error? (list 'not 'an 'error))))
    (is (true? (error? (list (symbol ";WARNING:") 'an 'error))))
    (is (true? (error? (list (symbol ";ERROR:") 'an 'error))))))

(deftest proteger-bool-en-str-test
  (testing "proteger-bool-en-str"
    (is (= "" (proteger-bool-en-str "")))
    (is (= "asdf" (proteger-bool-en-str "asdf")))
    (is (= "as%tdf" (proteger-bool-en-str "as#tdf")))
    (is (= "as%tdf" (proteger-bool-en-str "as%tdf")))))

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

(deftest fnc-sumar-test
  (testing "fnc-sumar"
    (is (= 0 (fnc-sumar ())))
    (is (= 3 (fnc-sumar '(3))))
    (is (= 7 (fnc-sumar '(3 4))))
    (is (= 12 (fnc-sumar '(3 4 5))))
    (is (= 18 (fnc-sumar '(3 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-1 '+ 'A) (fnc-sumar '(A 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '+ 'A) (fnc-sumar '(3 A 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '+ 'A) (fnc-sumar '(3 4 A 6))))))

(deftest fnc-restar-test
  (testing "fnc-restar"
    (is (= (generar-mensaje-error :wrong-number-args-oper '-) (fnc-restar ())))
    (is (= -3 (fnc-restar '(3))))
    (is (= -1 (fnc-restar '(3 4))))
    (is (= -6 (fnc-restar '(3 4 5))))
    (is (= -12 (fnc-restar '(3 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-1 '- 'A) (fnc-restar '(A 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '- 'A) (fnc-restar '(3 A 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '- 'A) (fnc-restar '(3 4 A 6))))))

(deftest fnc-menor-test
  (testing "fnc-menor"
    (is (= "#t" (fnc-menor ())))
    (is (= "#t" (fnc-menor '(3))))
    (is (= "#t" (fnc-menor '(3 4))))
    (is (= "#t" (fnc-menor '(3 4 5))))
    (is (= "#t" (fnc-menor '(3 4 5 6))))
    (is (= "#f" (fnc-menor '(3 4 4 6))))
    (is (= "#f" (fnc-menor '(3 4 1 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-1 '< 'A) (fnc-menor '(A 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '< 'A) (fnc-menor '(3 A 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '< 'A) (fnc-menor '(3 4 A 6))))))

(deftest fnc-mayor-test
  (testing "fnc-mayor"
    (is (= "#t" (fnc-mayor ())))
    (is (= "#t" (fnc-mayor '(1))))
    (is (= "#t" (fnc-mayor '(2 1))))
    (is (= "#t" (fnc-mayor '(3 2 1))))
    (is (= "#t" (fnc-mayor '(4 3 2 1))))
    (is (= "#f" (fnc-mayor '(4 2 2 1))))
    (is (= "#f" (fnc-mayor '(4 4 1 4))))
    (is (= (generar-mensaje-error :wrong-type-arg-1 '> 'A) (fnc-mayor '(A 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '> 'A) (fnc-mayor '(3 A 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '> 'A) (fnc-mayor '(3 2 A 6))))))
 
(deftest fnc-mayor-o-igual-test
  (testing "fnc-mayor-o-igual"
    (is (= "#t" (fnc-mayor-o-igual ())))
    (is (= "#t" (fnc-mayor-o-igual '(1))))
    (is (= "#t" (fnc-mayor-o-igual '(2 1))))
    (is (= "#t" (fnc-mayor-o-igual '(3 2 1))))
    (is (= "#t" (fnc-mayor-o-igual '(4 3 2 1))))
    (is (= "#t" (fnc-mayor-o-igual '(4 2 2 1))))
    (is (= "#f" (fnc-mayor-o-igual '(4 4 1 4))))
    (is (= (generar-mensaje-error :wrong-type-arg-1 '>= 'A) (fnc-mayor-o-igual '(A 4 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '>= 'A) (fnc-mayor-o-igual '(3 A 5 6))))
    (is (= (generar-mensaje-error :wrong-type-arg-2 '>= 'A) (fnc-mayor-o-igual '(3 2 A 6))))))

(deftest evaluar-escalar-test
  (testing "evaluar-escalar"
    (is (= '(32 (x 6 y 11 z "hola")) (evaluar-escalar 32 '(x 6 y 11 z "hola"))))
    (is (= '("chau" (x 6 y 11 z "hola")) (evaluar-escalar "chau" '(x 6 y 11 z "hola"))))
    (is (= '(11 (x 6 y 11 z "hola")) (evaluar-escalar 'y '(x 6 y 11 z "hola"))))
    (is (= '("hola" (x 6 y 11 z "hola")) (evaluar-escalar 'z '(x 6 y 11 z "hola"))))
    (is (= (list (generar-mensaje-error :unbound-variable 'n) '(x 6 y 11 z "hola")) (evaluar-escalar 'n '(x 6 y 11 z "hola"))))
        ))

(deftest evaluar-define-test
  (testing "evaluar-define"
    (is (= (list (symbol "#<unspecified>") '(x 2)) (evaluar-define '(define x 2) '(x 1))))
    (is (= (list (symbol "#<unspecified>") '(x 1 f (lambda (x) (+ x 1))) (evaluar-define '(define (f x) (+ x 1)) '(x 1)))))
    (is (= (list (generar-mensaje-error :missing-or-extra 'define '(define)) '(x 1)) (evaluar-define '(define) '(x 1))))
    (is (= (list (generar-mensaje-error :missing-or-extra 'define '(define x)) '(x 1)) (evaluar-define '(define x) '(x 1))))
    (is (= (list (generar-mensaje-error :missing-or-extra 'define '(define x 2 3)) '(x 1)) (evaluar-define '(define x 2 3) '(x 1))))
    (is (= (list (generar-mensaje-error :missing-or-extra 'define '(define ())) '(x 1)) (evaluar-define '(define ()) '(x 1))))
    (is (= (list (generar-mensaje-error :bad-variable 'define '(define () 2)) '(x 1)) (evaluar-define '(define () 2) '(x 1))))
    (is (= (list (generar-mensaje-error :bad-variable 'define '(define 2 x)) '(x 1)) (evaluar-define '(define 2 x) '(x 1))))
    ))
