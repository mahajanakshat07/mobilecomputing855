/*
 * CS820: Aritificial Intelligence
 * Student name:
 * Student ID:
 * Assignment 3 - CSP RB Instance Program
 */
package ai_assign3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class CSPInstances
{
    public int variables;
    public double constraintTightness;
    public double alpha;
    public ArrayList<String> createIncompatibleList;
    public int compatibleConstraints;
    public double rConstant;
    public int domainSize;
    public Map<String, String> contraintsList;
    public ArrayList<String> createConstraintsList;
    public String[] values;
    public ArrayList<Integer> domainValues;
    public int incompatibleConstraints;

    public static void main(String[] args)
    {
        CSPInstances obj = new CSPInstances(4,0.33,0.8,0.7);
    }

    /*
     * RB generation
     */
    public  CSPInstances(int n, double p, double a, double r)
    {
        variables = n;
        constraintTightness = p;
        alpha = a;
        rConstant = r;
        values = new String[n];
        for(int i=0; i < n; i++)
        {
            values[i] = "X"+Integer.toString(i);
        }

        /*
         * Calculating domain size, No. of constraints and incompatibleTupl tuples.
         */
        domainSize = (int)Math.round(Math.pow(n, a));
        compatibleConstraints = (int)Math.round(r * n * Math.log(n));
        incompatibleConstraints = (int)Math.round(p * Math.pow(domainSize, 2));
        createConstraintsList = new ArrayList();
        createIncompatibleList = new ArrayList();
        domainValues = new ArrayList();
        contraintsList = new HashMap<String, String>();
        createConstraints();
        createDomainValues();
        PrintCSPInstance();
    }

    public void PrintCSPInstance()
    {
        System.out.println("\n************** Printing Input Parameters **************\n");
        displayParams();
        System.out.println("\nConstraint : Incompatible Tuples");
        if(createConstraintsList.isEmpty() ==false)
        {
            for(int i=0; i < createConstraintsList.size(); i++)
            {
                contraintsList.put(createConstraintsList.get(i), createIncompatibleList.get(i));
                System.out.println("("+createConstraintsList.get(i)+"): "+createIncompatibleList.get(i).replace("~","  "));
            }
        }
    }

    public void createConstraints()
    {
        ArrayList<Integer> value = new ArrayList();
        String tempVariables = "";
        ArrayList<String> possibleVariables = new ArrayList();
        ArrayList<String> possibleConstraints = new ArrayList();
        String tempStr = "";
        String res = "";
        String str1="", str2="";
        String tempStr1="", tempStr2="";
        boolean checkval = true;
        for(int i=0; i < compatibleConstraints; i++)
        {
            str1 = values[rangeDifference(0,variables-1)];
            str2 = values[rangeDifference(0,variables-1)];
            while(str1.equalsIgnoreCase(str2))
            {
                str1 = values[rangeDifference(0,variables-1)];
                str2 = values[rangeDifference(0,variables-1)];
            }
            tempStr1 = str1+str2;
            checkval = (subsistAvailable(tempStr1,possibleVariables) || subsistAvailable(str2+str1,possibleVariables) );
            if(checkval == true)
            {
                while(checkval == true)
                {
                    str1 = values[rangeDifference(0,variables-1)];
                    str2 = values[rangeDifference(0,variables-1)];
                    while(str1.equalsIgnoreCase(str2))
                    {
                        str1 = values[rangeDifference(0,variables-1)];
                        str2 = values[rangeDifference(0,variables-1)];
                    }
                    tempStr1 = str1+str2;
                    checkval = (subsistAvailable(tempStr1,possibleVariables) || subsistAvailable(str2+str1,possibleVariables) );
                }
            }
            possibleVariables.add(tempStr1);
            createConstraintsList.add(str1+","+str2);
            for(int h=0; h < incompatibleConstraints; h++)
            {
                tempStr = Integer.toString(rangeDifference(0,domainSize-1)) + ","+Integer.toString(rangeDifference(0,domainSize-1));
                checkval = subsistAvailable(tempStr,possibleConstraints);
                if(checkval == true)
                {
                    while(checkval == true)
                    {
                        tempStr = Integer.toString(rangeDifference(0,domainSize-1)) + ","+
                                Integer.toString(rangeDifference(0,domainSize-1));
                        checkval = subsistAvailable(tempStr,possibleConstraints);
                    }
                }
                possibleConstraints.add(tempStr);
                tempStr += "~";
                res += tempStr;
            }
            possibleConstraints.clear();
            res = res.substring(0,res.length()-1);
            createIncompatibleList.add(res);
            res = "";
        }
    }

    public void createDomainValues()
    {
        for(int i=0; i < domainSize; i++)
        {
            domainValues.add(i);
        }
    }

    public void printArray(String[] var)
    {
        if(var.length > 0)
        {
            for(int i=0; i< var.length; i++)
            {
                System.out.println("Key: "+i+" Value :"+var[i]);
            }
        }
        else
        {
            System.out.println(" Array is Empty!!");
        }
    }

    public boolean subsistAvailable(String temp,ArrayList<String> values)
    {
        boolean stateBool = false;
        if( values.isEmpty()==false)
        {
            for(int i=0; i < values.size(); i++)
            {
                if(values.get(i).equalsIgnoreCase(temp))
                {
                    stateBool = true;
                }
            }
        }
        return stateBool;
    }

    /*
     * Displaying the user input parameters.
     */
    public void displayParams()
    {
        System.out.println("Number of variables(n)  : "+variables);
        System.out.println("Constraint Tightness(p) : "+constraintTightness);
        System.out.println("Constant alpha(a)       : "+alpha);
        System.out.println("Constant (r)            : "+rConstant);
        /*
         * Displaying RB instance.
         */
        System.out.println("\n************** Generated RB Instance ***************\n");
        System.out.println("Size of Domain(n to the power alpha): "+domainSize);
        System.out.println("Number of Constraints               : "+compatibleConstraints);
        System.out.println("Number of Incompatible tuples       : "+incompatibleConstraints+"\n");

        String tempVar="Variables  : {";
        if(values[0] != null)
        {
            for(int i=0; i < values.length; i++)
            {
                tempVar += values[i];
                tempVar += ", ";
            }
        }
        tempVar = tempVar.substring(0, tempVar.length() -2);
        tempVar += "}";
        System.out.println(tempVar);
        tempVar = "Domain : {";
        if(domainValues.isEmpty()==false)
        {
            for(int i=0; i < domainValues.size(); i++)
            {
                tempVar += domainValues.get(i);
                tempVar += ", ";
            }
        }
        tempVar = tempVar.substring(0, tempVar.length() -2);
        tempVar += "}";
        System.out.println(tempVar);
    }

    public int rangeDifference(int min, int max)
    {
        int length = (max - min) + 1;
        return (int)(Math.random() * length) + min;
    }
}


class SolvingTechniques
{
    public static Integer[] checkItems(Integer[] tempArray1, Integer[] tempArray2)
    {
        Integer[] var1;
        ArrayList<Integer> newArray = new ArrayList();
        for(int i=0; i < tempArray1.length; i++)
        {
            if(CheckEquality(tempArray1[i], tempArray2) == false)
            {
                newArray.add(tempArray1[i]);
            }
        }
        var1 = new Integer[newArray.size()];
        if(!newArray.isEmpty())
        {
            for(int i=0; i < newArray.size(); i++)
            {
                var1[i] = newArray.get(i);
            }
        }
        return var1;
    }

    public static Integer[] createListInteger(ArrayList<Integer> var)
    {
        int length = var.size();
        Integer[] arrayList = new Integer[length];
        for(int i=0; i < var.size(); i++)
        {
            arrayList[i] = var.get(i);
        }
        return arrayList;
    }

    public static Integer[] createIntegerList(ArrayList<String> var)
    {
        int length = var.size();
        Integer[] newList = new Integer[length];
        for(int i=0; i < var.size(); i++)
        {
            newList[i] = Integer.parseInt(var.get(i));
        }
        return newList;
    }

    public static String[] createList(ArrayList<Integer> var)
    {
        int length = var.size();
        String[] newList = new String[length];
        for(int i=0; i < var.size(); i++)
        {
            newList[i] = Integer.toString(var.get(i));
        }
        return newList;
    }

    public static String[] loadArray(ArrayList<String> var)
    {
        int status = var.size();
        String[] newList = new String[status];
        for(int i=0; i < var.size(); i++)
        {
            newList[i] = var.get(i);
        }
        return newList;
    }

    public static boolean checkArrays(Integer[] array1, Integer[] array2)
    {
        int counter=0;
        boolean stateBool = false;
        if(array1.length == array2.length)
        {
            for(int i=0; i < array1.length; i++)
            {
                if(CheckEquality(array1[i], array2) == false)
                {
                    counter++;
                }
            }
        }
        else
        {
            counter = 1;
        }
        if(counter < 1)
        {  stateBool = true;  }

        return stateBool;
    }

    public static boolean sameArray(String[] array1, String[] array2)
    {
        int status=0;
        boolean check = false;
        if(array1.length == array2.length)
        {
            for(int i=0; i < array1.length; i++)
            {
                if(checkStatus(array1[i], array2,0) == false)
                {
                    status++;
                }
            }
        }
        else
        {
            status = 1;
        }
        if(status < 1)
        {  check = true;  }
        return check;
    }

    public static ArrayList joinList(ArrayList<Integer> firstList, ArrayList<Integer> secondList)
    {
        int tempValue = -1, nodeValue = -1;
        ArrayList<Integer> tempVar = new ArrayList();
        boolean stateBool=false;
        if(firstList != null && firstList.size() > 0)
        {
            for(int i=0; i< firstList.size(); i++)
            {
                tempVar.add(firstList.get(i));
            }
        }
        if(secondList != null && secondList.size() > 0)
        {
            if(tempVar.size() > 0)
            {
                for(int i=0; i < secondList.size(); i++)
                {
                    nodeValue = secondList.get(i);
                    for(int y=0; y < tempVar.size(); y++)
                    {
                        if(nodeValue == tempVar.get(y))
                        {
                            stateBool = true;
                        }
                    }
                    if(stateBool == false)
                    {
                        tempVar.add(nodeValue);
                    }
                    stateBool = false;
                }
            }
            else
            {   tempVar = secondList; }
        }
        return tempVar;
    }

    public static int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    public static boolean checkStatus(String item,String[] values,  int temp)
    {
        boolean stateBool = false;
        if( values[0] != null )
        {
            for(int i=0; i < values.length; i++)
            {
                switch(temp)
                {
                    case 0:
                        if(values[i].equalsIgnoreCase(item))
                        {  stateBool = true;  }
                        break;
                    case 1:
                        if(values[i].compareTo(item) == 0)
                        {  stateBool = true;  }
                        break;
                }
            }
        }
        return stateBool;
    }

    public static boolean CheckEquality(Integer item, Integer[] vals)
    {
        boolean stateBool = false;
        if( vals[0] != null )
        {
            for(int i=0; i < vals.length; i++)
            {
                if(vals[i].equals(item))
                {  stateBool = true;  }
            }
        }
        return stateBool;
    }
}

