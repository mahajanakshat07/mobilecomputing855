#This file is used to take input from User.
#

from unification import unification


#take input from user
term1 = raw_input(" Enter First Term : ex : f(f(X,Y),X) ")

term2 = raw_input("Enter Second Term : ex : f(f(X,Y),X)")

term1=term1.replace(" ", "")
term2=term2.replace(" ","")
print("First Term=  "+term1)
print("Second Term=  "+term2)


term1=term1.replace(" ", "")
term2=term2.replace(" ","")
print("First Term=  "+term1)
print("Second Term=  "+term2)

print()

unification(term1,term2)

