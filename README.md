# Promotions_for_PaymentMethods
Rozwiązanie zadania opiera się na zastosowaniu heurystyki, gdyż doszedłem do wniosku, że problem może nie mieć optymalnego rozwiązania w czasie wielomianowym. Zastosowałem dosyć prostą heurystykę. <br />  <br /> 
Posortowałem metod płatności w pierwszej kolejności po obniżce, w drugiej kolejności po limicie transakcji. Tak samo posortowałem zamówienia, w pierwszej kolejności po ich cenie, w drugiej kolejności po ilości metod płatności, które obsługują. <br />  <br /> 
Następnie wiedząc, że metody płatności dające wyższą obniżkę niż punkty na pewno muszę gdzieś wykorzystać (pomimo założenia o minimalizacji ilości płatności kartą), zachłannie za ich pomocą obsługiwałem zamówienia, na których mogę je wykorzystać, <br />  począwszy od tych z największą wartością. 
Następnie do pozostałych zamówień zaaplikowałem punkty, również zachłannie obsługując od największych. Taki sam krok wykonałem dla metod płatności mniej opłacalnych niż punkty, ale dalej dających większą lub równą obniżkę niż 10 %. <br />  <br /> 
Później do zamówień jeszcze nieobsłużonych zastosowałem punkty zachłannie, wykorzystując dla każdego 10 % jego wartości, gdyż jest to minimalna ilość zapewniająca rabat. <br /> <br /> 
Tuż przed zakończeniem próbowałem zastosować metody płatności o obniżce mniejszej niż 10 % do zamówień, które ani nie zostały całkowicie, ani częściowo obsłużone. <br /> <br /> 
Na zakończenie wypełniłem brakujące kwoty, w zamówieniach które pozostały nieobsłużone lub zostało zapłacone tylko w 20 % przez użycie  punktów, sposób wypełnienia pod sam koniec już nie miał znaczenia, gdyż heurystyka zakończyła swoje działanie i ważne było jedynie aby suma limitów metod płatności przewyższała sumę wartości zamówień. <br /> <br /> 
Alternatywą do tej heurystyki mogłoby być zastosowanie w niektórych miejscach algorytmu aproksymacyjnego do przybliżenia rozwiązania problemu plecakowego, tak aby to rozwiązanie nie było zależne od pojemności (w tym przypadku limitów transakcji, które mogą być duże i do klasycznego problemu plecakowego i tak wymagane byłoby ich przmnożenie przez 100 z racji części ułamkowej). <br /> <br />
Jednak nawet zakładając, że limity byłyby ograniczone i mozliwe byłoby zastosowanie nie-aproksymacyjnego problemu plecakowego to i tak należałoby uwzględnić zależności między zamówieniami a opcjami płatności. Można wskazać następujący kontrprzykład: <br />
Lista zamówień (podane jako ID, potem wartość i potem obsługiwane metody transakcji):<br /> <br />
Order1, 100, [t1, t2] <br />
Order2, 150, [t1] <br />
Order3, 90, [t1] <br/> <br />
Lista metod płatności(podane jako ID, potem procent rabatu, potem limit):<br /> <br />
t1, 30, 250 <br />
t2, 10, 100 <br />
PUNKTY, 6, 150 <br /> <br />
W tym przypadku nie opłaca się zastosować nawet problemu plecakowego dla najbardziej opłacalnej metody płatności, bo jednak bardziej opłaca się wziąć za pomocą t1: Order2 i Order3, a za pomocą t2: Order2. <br />
Jeśli by wziąć za pomocą t1: Order1 i Order2, to później nie możmy wziąć Order3 za pomocą t2. <br />
W przypadku powyższym mamy max_profit 75 + 6 = 81, a w poprzednim 72 + 10 = 82 <br /> <br />
Wydaje mi się również, że zastosowanie algorytmów związanych z problemem maksymalnego przepływu typu min cost max flow również nie będzie miało tutaj zastosowania, ponieważ wybór scieżek poszerzających może nie być optymalny <br /> + dodatkowo mamy warunek, że aby uzyskać rabaty musimy całkowicie uiścić opłatę punktami albo kartą, a przepływ raczej nie zadziała w ten sposób <br /> <br />
Moim zdaniem rozwiązaniem, które mogłoby dać znacznie lepszy wynik mogłoby być zastosowanie ILP ze zmiennymi binarnymi, i maksymalizowaniem wyrażanie będącego sumą rabatów przy jednoczesnym minimalizowaniu użycia płatności kartą. Należałoby wówczas zastosować być może jakieś wagi do obu parametrów 
tak aby przy tym samym rabacie premiować rozwiązania używające mniej karty, ale jednocześnie aby gorsze pod tym względem rozwiązanie nie było w stanie przewyższyć lepszego używając znacznie mniej razy płatności kartą. <br /> <br />
Nie zdecydowałem się zastosować takiego podejścia tutaj, gdyż sądzę, że byłoby ono zbyt wolne. <br />

