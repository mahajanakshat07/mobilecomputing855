# AI Assignment-2
# Krishna Murthy S
# Student id: 200428500

import time
import math
import random
from copy import deepcopy

class Stack:                                                                   # Stack used to save the nodes
    def __init__(self):
         self.items = []

    def isEmpty(self):
         return self.items == []

    def push(self, item):
         self.items.append(item)

    def pick(self):
         return self.items[len(self.items)-1]
    
    def pop(self):
         return self.items.pop()


def CSPGenerator(n, domainSize, noOfConstraints, noOfIncompatibleTuple):      # Block to Generate the CSPs
    
    incomTupForEachCons = []
    
    for i in range(n):
        variableList.append("x" + str(i))
        defaultVariableList.append("x" + str(i))
     
    
    for i in range(domainSize):
        domainList.append(i)
        defaultDomainList.append(i)
     
   
    for i in range(noOfConstraints):
        
        random.shuffle(variableList)                                    # Randomizing the generated pairs
        
        while True:
            pair = random.sample(set(variableList), 2)
            
            if pair not in constraintsList:                             # Checking if the generated pair is unique
                pair = [pair[1], pair[0]]
                if pair not in constraintsList:
                    constraintsList.append(pair)
                    
                    for i in range(noOfIncompatibleTuple):              # Generating icompatible Tuples
                         
                        random.shuffle(domainList)
                        incompatibleTuplePair = random.sample(set(domainList), 2)
                        while True:
                            
                            incompatibleTuplePair = random.sample(set(domainList), 2)   # Generate incompatible tuple until it is unique
                            if incompatibleTuplePair not in incomTupForEachCons: 
                                incomTupForEachCons.append(incompatibleTuplePair)
                                break
                        if i >=(noOfIncompatibleTuple-1):
                            incompatibleTupleList.append(incomTupForEachCons)
                            incomTupForEachCons = []
                        
                    break
                else:
                    pair = [pair[1], pair[0]]
                    print ("reverse pair generated", pair)
    
    
    return defaultVariableList, defaultDomainList, constraintsList, incompatibleTupleList 
    
  
def arcConsistency(domainSize, defaultVariableList, defaultDomainList, constraintsList, incompatibleTupleList):     #Implementing Arc consistency
    allPossibleTuple = []
    allPossibleTupleTemp = []
    
    for i in range(domainSize):
        for j in range(domainSize):
            pair = [i,j]
            allPossibleTuple.append(pair)
    iterationAllTuple = len(allPossibleTuple)
    iterationIncompatibleTuple = len(incompatibleTupleList)
    
    for i in range(iterationIncompatibleTuple):                 # Generating only the compatible tuples to reduce domain size
        for j in range(iterationAllTuple):
            if allPossibleTuple[j] not in incompatibleTupleList[i]:
                allPossibleTupleTemp.append(allPossibleTuple[j])
            if j>=iterationAllTuple-1:
                allTrueTuple.append(allPossibleTupleTemp)
                allPossibleTupleTemp = []
    return allTrueTuple                                         # The new reduced size of Tuples is obtained here
    
    
        
def ruleChecker(temp, currentNode, constraintsList, allTrueTuple, choice):      # It checks each pair of constraints value with its corresponding tuples to find a solution
     
    flag = 1
    list1 = [i for i in temp if isinstance(i, (int))]
    limit = len(list1) - 1
    
    for i in range(limit, 0, -1):
        pair = ['x' + str(limit), 'x' + str(i-1)]
        pairReverse = ['x' + str(i-1), 'x' + str(limit)]
        
        if pair in constraintsList:
            index = constraintsList.index(pair)
            tempPair = [temp[limit], temp[i-1]]
            
            if choice == 1:
                if tempPair in allTrueTuple[index]:
                    continue
                else:
                    flag = 0
                    
            else:
                if tempPair not in incompatibleTupleList[index]:
                    continue
                else:
                    flag = 0
                    break
                
                
        elif pairReverse in constraintsList:
            index = constraintsList.index(pairReverse)
            tempPair = [temp[i-1], temp[limit]]
            
            if choice == 1:                                         # Checks all the tuples with arc consistency
                if tempPair in allTrueTuple[index]:
                    continue
                else:
                    flag = 0
                    break
                
            else:                                                   # Checks all the tuples without arc consistency
                if tempPair not in incompatibleTupleList[index]:
                    continue
                else:
                    flag = 0
                    break
            
        else:
            flag = 1
        
    
    if flag == 1:
        return flag
    else:
        return flag    
    
     
def standardBTwithoutArc(n, defaultDomainList, constraintsList, incompatibleTupleList, currentNode, choice):       # Back tracking without arc
    node = deepcopy(listOfallPairs[currentNode])
    list1 = [i for i in node if isinstance(i, (int))]
    placeToChange = len(list1)
    iterator = len(defaultDomainList)
    
    for i in range(iterator):                           # Generating all the childs of a given parent node
        node[placeToChange] = i
        s.push(node)
        node = deepcopy(listOfallPairs[currentNode])
        
        if i >= iterator-1:
            temp = deepcopy(s.pop())                    # Pop out the last child from the stack and check if the child is valid or not
            flag = ruleChecker(temp, currentNode, constraintsList, incompatibleTupleList, choice)
            while True:
                if flag == 1:
                    listOfallPairs.append(temp)         # If child valid save to an array
                    break
                
                else:                                   # If child is invalid pop the next child and check for validity
                    temp = deepcopy(s.pop())
                    flag = ruleChecker(temp, currentNode, constraintsList, incompatibleTupleList, choice)
                    continue
            
            currentNode += 1
            if listOfallPairs[currentNode][-1] is None:
                standardBTwithoutArc(n, defaultDomainList, constraintsList, incompatibleTupleList, currentNode, choice) 
            else:
                print ("Solution: ", listOfallPairs[currentNode])
                return True
                
                        
def standardBTwithArc(n, defaultDomainList, constraintsList, allTrueTuple, currentNode, choice):   # Back tracking with arc

    node = deepcopy(listOfallPairs[currentNode])
    list1 = [i for i in node if isinstance(i, (int))]
    placeToChange = len(list1)
    iterator = len(defaultDomainList)
    
    for i in range(iterator):                           # Generating all the childs of a given parent node
        node[placeToChange] = i
        s.push(node)
        node = deepcopy(listOfallPairs[currentNode])
        
        if i >= iterator-1:
            temp = deepcopy(s.pop())                    # Pop out the last child from the stack and check if the child is valid or not
            flag = ruleChecker(temp, currentNode, constraintsList, allTrueTuple, choice)    # For arc check for allTrueTuple instead of incompatible tuple
            while True:
                if flag == 1:
                    
                    listOfallPairs.append(temp)         # If child is valid save to an array
                    break
                
                else:                                   # If not pop next and keep repeating with the allTrueTuple list to obtain arc consistency
                    temp = deepcopy(s.pop())
                    flag = ruleChecker(temp, currentNode, constraintsList, allTrueTuple, choice)
                    continue
            
            currentNode += 1
            if listOfallPairs[currentNode][-1] is None:
                standardBTwithArc(n, defaultDomainList, constraintsList, allTrueTuple, currentNode, choice) 
            else:
                print ("Solution: ", listOfallPairs[currentNode])
                return True


def FCwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice):   #Forward checking without arc
    
    counter += 1
    
    list1 = [i for i in node if isinstance(i, (int))]       # Identify the valid input in the statring node itself
    placeToChange = len(list1)
    
    node[placeToChange] = defaultDomainList[domainValue]    # Generate the next node
    
    flag = ruleChecker(node, currentNode, constraintsList, incompatibleTupleList, choice)    # Check if the node is valid
    
    if flag == 1:                                           # If node is valid then start do
        domainValue = placeToChange
        s.push(deepcopy(node))                              # Push node into stack
        if node[-1] is None:
            FCwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
        else:
            print ("Solution: ", node)
            return True
    
    else:                                                   # If the node is not valid
        
        list1 = [i for i in node if isinstance(i, (int))]
        placeToChange = len(list1)
        placeToChange -= 1
        
        if node[placeToChange] == defaultDomainList[-1]:    # If all children are genrated and still it fails
            while True:
                if node[placeToChange] == defaultDomainList[-1]:
                    node[placeToChange] = None
                    placeToChange -= 1
                    #print (node, "from while-if")
                    continue
                else:
                    value = node[placeToChange]
                    value += 1
                    domainValue = value
                    node[placeToChange] = domainValue
                    domainValue = placeToChange
                    FCwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
                    break
        
        else:                                              # Generate the next child
            value = node[placeToChange]
            value += 1
            domainValue = value
            node[placeToChange] = None
            FCwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)

    
def FCwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice):   # Forward checking with arc
    counter += 1
    
    list1 = [i for i in node if isinstance(i, (int))]       # Identify the valid input in the statring node itself
    placeToChange = len(list1)
    node[placeToChange] = defaultDomainList[domainValue]
    
    flag = ruleChecker(node, currentNode, constraintsList, allTrueTuple, choice)    # Check if the node is valid
    
    if flag == 1:
        domainValue = placeToChange
        s.push(deepcopy(node))                              # Push node into stack
        if node[-1] is None:
            FCwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
        else:
            print ("Solution: ", node)
            return True
    else:
        
        list1 = [i for i in node if isinstance(i, (int))]
        placeToChange = len(list1)
        placeToChange -= 1
        
        if node[placeToChange] == defaultDomainList[-1]: 
            while True:
                if node[placeToChange] == defaultDomainList[-1]:
                    node[placeToChange] = None
                    placeToChange -= 1
                    continue
                else:
                    value = node[placeToChange]
                    value += 1
                    domainValue = value
                    node[placeToChange] = domainValue
                    domainValue = placeToChange
                    FCwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
                    break
        else:
            value = node[placeToChange]
            value += 1
            domainValue = value
            node[placeToChange] = None
            FCwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
                
        
        
def FLAwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice):    # FLA without arc
    counter += 1
    
    list1 = [i for i in node if isinstance(i, (int))]       # checks the valid input
    placeToChange = len(list1)
    
    node[placeToChange] = defaultDomainList[domainValue]    # Generates the next node and checks if it is valid or not
    flag = ruleChecker(node, currentNode, constraintsList, allTrueTuple, choice)
    
    if flag == 1:
        domainValue = placeToChange
        if node[-1] is None:
            FLAwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
        else:
            print ("Solution: ", node)
            return True
    else:
        
        list1 = [i for i in node if isinstance(i, (int))]
        placeToChange = len(list1)
        placeToChange -= 1
        
        if node[placeToChange] == defaultDomainList[-1]: 
            while True:
                if node[placeToChange] == defaultDomainList[-1]:
                    node[placeToChange] = None
                    placeToChange -= 1
                    continue
                else:
                    value = node[placeToChange]
                    value += 1
                    domainValue = value
                    node[placeToChange] = domainValue
                    domainValue = placeToChange
                    FLAwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
                    break
        else:
            value = node[placeToChange]
            value += 1
            domainValue = value
            node[placeToChange] = None
            FLAwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
    

def FLAwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice):     # FLA with arc
    
    counter += 1
    list1 = [i for i in node if isinstance(i, (int))]
    placeToChange = len(list1)
     
    node[placeToChange] = defaultDomainList[domainValue]
    
    flag = ruleChecker(node, currentNode, constraintsList, allTrueTuple, choice)
    
    if flag == 1:
        domainValue = placeToChange                         # Checking for allTrueTuple as we are working with arc
        if node[-1] is None:
            FLAwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
        else:
            print ("Solution: ", node)
            return True
    else:
        
        list1 = [i for i in node if isinstance(i, (int))]
        placeToChange = len(list1)
        placeToChange -= 1
        
        if node[placeToChange] == defaultDomainList[-1]: 
            while True:                                     # Remove the last element in node when the element is max value of domain
                if node[placeToChange] == defaultDomainList[-1]:
                    node[placeToChange] = None
                    placeToChange -= 1
                    continue
                else:
                    value = node[placeToChange]
                    value += 1
                    domainValue = value
                    node[placeToChange] = domainValue
                    domainValue = placeToChange
                    FLAwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
                    break
       
        else:
            value = node[placeToChange]
            value += 1
            domainValue = value
            node[placeToChange] = None
            FLAwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
        
        
print ("Number of variables:")                              # User input
n = int(input())
print ("Constraint tightness:")
p = float(input())
print ("Constant alpha:")
a = float(input())
print ("Constant r:")
r = float(input())

s = Stack()
domainSize = int(round(math.pow(n, a)))                     # Computing domain size and number of constraints
noOfConstraints = int(round(r*n*math.log(n)))
noOfIncompatibleTuple = int(round(p*domainSize*domainSize)) # Computing the number of Incompatible Tuples

variableList = []
defaultVariableList = []
domainList = []
defaultDomainList = []
constraintsList = []
incompatibleTupleList = []
allTrueTuple = []
CSPGenerator(n, domainSize, noOfConstraints, noOfIncompatibleTuple)       # Generate the CSP to work with
arcConsistency(domainSize, defaultVariableList, defaultDomainList, constraintsList, incompatibleTupleList)  # If asked for arc consistency

currentNode = 0
listOfallPairs = []
solutionPair = [None] * n
s.push(solutionPair)
listOfallPairs = [s.pop()]
listOfallPairs[0][0] = 0

domainValue = counter = failCounter = 0
solutionPair = [None] * n
solutionPair[0] = 0
node = solutionPair
s.push(deepcopy(node))

print ("\n")                                                              # output generations
print ("Variables:", defaultVariableList)
print ("Domain:", defaultDomainList)
print ("Constraints: Incompatible Tuples")
for i in range(len(constraintsList)):
    print (constraintsList[i], " : ", incompatibleTupleList[i])
print ("\n")     

print ("1-Standard Backtracking, 2-Forward Checking, 3-Full Look Ahead")  # Choosing the solving method
preference = int(input())
if preference == 1:
    print ("Choose 1 for Backtracking with arc consistency and 2 without arc consistency:")
    choice = int(input())
    if choice == 1:
        start = time.time()
        standardBTwithArc(n, defaultDomainList, constraintsList, allTrueTuple, currentNode, choice)
        stop = time.time()
        print ("Time required:", stop-start)
    else:
        start = time.time()
        standardBTwithoutArc(n, defaultDomainList, constraintsList, incompatibleTupleList, currentNode, choice)
        stop = time.time()
        print ("Time required:", stop-start)
        
elif preference == 2:
    print ("Choose 1 for Forward Checking with arc consistency and 2 without arc consistency:")
    choice = int(input())
    
    if choice == 1:
        
        start = time.time()
        FCwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
        stop = time.time()
        print ("Time required:", stop-start)
    else:
        
        start = time.time()
        FCwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
        stop = time.time()
        print ("Time required:", stop-start)
    
else:
    print ("Choose 1 for Full Look Ahead with arc consistency and 2 without arc consistency:")
    choice = int(input())
    
    if choice == 1:
        start = time.time()
        FLAwithArc(failCounter, counter, node, defaultDomainList, constraintsList, allTrueTuple, domainValue, choice)
        stop = time.time()
        print ("Time required:", stop-start)
    else:
        start = time.time()
        FLAwithoutArc(failCounter, counter, node, defaultDomainList, constraintsList, incompatibleTupleList, domainValue, choice)
        stop = time.time()
        print ("Time required:", stop-start)
