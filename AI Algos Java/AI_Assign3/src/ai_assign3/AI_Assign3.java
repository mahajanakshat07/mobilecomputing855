package ai_assign3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author
 */

/*
 * Main Class of the program.
 */
public class AI_Assign3
{
    public static int size = 0;
    public static Map<String, ArrayList<String>> container = new HashMap<String, ArrayList<String>>();
    public static Map<String, ArrayList<Integer>> domainValue =  new HashMap<String, ArrayList<Integer>>();
    public static Map<String, String> constraints = new HashMap<String, String>();
    public static String[] nodes;

    public static void main(String[] args)
    {
        String statSame = "";
        String[] statTemp;
        long timeDiff;
        int n = 0;
        double p = 0;
        String tempRead = "";
        double a = 0;
        String ArcStat = "";
        double r = 0;
        String techniqueState = "";
        try{
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

            /*
             * Getting input parameters (n, p, a, r) from the user.
             * Getting user's choice for the desired technique.
             * Asking if user wants to run Arc Consistency before backtracking.
             */
            System.out.println("Welcome !!! This is a program for CSP RB Instance generation and Solution.");
            System.out.println(" *************************************************************************");
            System.out.println("Enter Binary CSP instance Input Parameters (n, p, a, r)");
            System.out.println("Example Input (separated by comma and no spaces) : 4,0.33,0.7,0.8");

            System.out.println("\nPlease Enter values using above format: ");
            tempRead = read.readLine();

            System.out.println("\nThis program uses the following strategies: \nBT  - Standard Back Tracking \nFC  - Forward Checking \nFLA - Full Look Ahead");
            System.out.println("\nEnter the Solving Strategy (Enter 'BT' or 'FC' or 'FLA'): ");
            techniqueState = read.readLine();

            System.out.println("\nWant to apply Arc Consistency before Search?");
            System.out.println("Enter 'Y' for Yes and 'N' for No (Y/N): ");
            ArcStat = read.readLine();

            tempRead = tempRead.trim(); tempRead = tempRead.replace(" ","");
            statTemp = tempRead.split(",");
            n = Integer.parseInt(statTemp[0]);
            p = Double.parseDouble(statTemp[1]);
            a = Double.parseDouble(statTemp[2]);
            r = Double.parseDouble(statTemp[3]);
            long start_time = System.nanoTime();
            CSPInstances obj = new CSPInstances(n,p,a,r);
            constraints = obj.contraintsList;
            nodes = obj.values;
            for(int i=0; i < obj.values.length; i++)
            {
                domainValue.put(obj.values[i], obj.domainValues);
            }
            if(ArcStat.compareToIgnoreCase("y") == 0)
            {
                arcConsistencyRun();
            }
            if(techniqueState.compareToIgnoreCase("bt") == 0)
            {
                backTracking(0);
            }
            else
            if(techniqueState.compareToIgnoreCase("fc") == 0)
            {
                fwdChecking(0);
            }
            else
            if(techniqueState.compareToIgnoreCase("fla") == 0)
            {
                fullLookAhead(0);
            }

            long end_time = System.nanoTime();
            timeDiff = (long) ((end_time - start_time)/1e6);
            System.out.println("\nTime needed: "+(timeDiff)+" milisecs\n");
        }
        catch(Exception io)
        {
            System.out.println("Sorry no solution found. Try again.");
        }
    }

    /*
     * BT - BackTracking: Standard Backtracking.
     */
    public static void backTracking(int size)
    {
        Integer[] assignindexVal = null;
        Integer[] domainVal = SolvingTechniques.createListInteger(domainValue.get(nodes[size]));
        Integer[] unassignedIndexVal = null;
        int state = -1;
        boolean statusConstraint = true;
        if(!container.isEmpty() && container.containsKey(nodes[size]))
        {
            assignindexVal = SolvingTechniques.createIntegerList(container.get(nodes[size]));
        }
        if(assignindexVal != null && size == 0 && SolvingTechniques.checkArrays(domainVal, assignindexVal))
        {
            System.out.println("Sorry No Solution found. Try again.");
        }
        else
        {
            if(assignindexVal == null || assignindexVal.length < 1)
            {
                unassignedIndexVal = domainVal;
            }
            else
            {
                unassignedIndexVal = SolvingTechniques.checkItems(domainVal, assignindexVal);
            }
            for(int i=0; i < unassignedIndexVal.length; i++)
            {
                if(checkConstraint(size, unassignedIndexVal[i]) == false)
                {
                    state = unassignedIndexVal[i];
                    i = unassignedIndexVal.length;
                    statusConstraint = false;
                }
            }
            if(statusConstraint == true)
            {
                if(container.containsKey(nodes[size]))
                {
                    container.remove(nodes[size]);
                }

                if(size > 0)
                {   size --; }
                backTracking(size);
            }
            else
            {
                if(container.containsKey(nodes[size]))
                {
                    ArrayList<String> sv = container.get(nodes[size]);
                    sv.add(Integer.toString(state));
                    container.put(nodes[size], sv);
                }
                else
                {
                    ArrayList<String> sv = new ArrayList();
                    sv.add(Integer.toString(state));
                    container.put(nodes[size], sv);
                }
                if(size == (nodes.length-1))
                {
                    System.out.println("\n*****Printing the Output Values after searching.*****\n");
                    printVariables();
                }else
                {
                    size++;
                    backTracking(size);
                }
            }
        }
    }


    /*
     * FC: Forward Checking
     */
    public static void fwdChecking(int size)
    {
        boolean constraintCheck = true;
        Integer[] unAssignedVaues = null;
        int temp=0;
        Integer[] domainVal = SolvingTechniques.createListInteger(domainValue.get(nodes[size]));
        int selectedValue=-1;
        Integer[] assignValues = null;
        if(!container.isEmpty() && container.containsKey(nodes[size]))
        {
            assignValues = SolvingTechniques.createIntegerList(container.get(nodes[size]));
        }
        if(assignValues != null && size == 0 && SolvingTechniques.checkArrays(domainVal, assignValues))
        {
            System.out.println("Sorry No Solution found. Try again.");
        }
        else
        {
            if(assignValues == null || assignValues.length < 1)
            {
                unAssignedVaues = domainVal;
            }
            else
            {
                unAssignedVaues = SolvingTechniques.checkItems(domainVal, assignValues);
            }
            if(size==0)
            {
                temp = SolvingTechniques.randomWithRange(0, unAssignedVaues.length-1);
                selectedValue = unAssignedVaues[temp];
                constraintCheck = false;
            }
            else
            {
                for(int i=0; i < unAssignedVaues.length; i++)
                {
                    if(checkConstraint(size, unAssignedVaues[i]) == false)
                    {
                        selectedValue = unAssignedVaues[i];
                        i = unAssignedVaues.length;
                        constraintCheck = false;
                    }
                }
            }
            forwardCheckAC(size, selectedValue);
            if(constraintCheck == true)
            {
                if(container.containsKey(nodes[size]))
                {
                    container.remove(nodes[size]);
                }
                if(size > 0)
                {   size --; }
                fwdChecking(size);
            }
            else
            {
                if(container.containsKey(nodes[size]))
                {
                    ArrayList<String> sv = container.get(nodes[size]);
                    sv.add(Integer.toString(selectedValue));
                    container.put(nodes[size], sv);
                }
                else
                {
                    ArrayList<String> sv = new ArrayList();
                    sv.add(Integer.toString(selectedValue));
                    container.put(nodes[size], sv);
                }
                if(size == (nodes.length-1))
                {
                    System.out.println("\n*****Printing the Output Values after searching.*****\n");   // Displays the found output
                    printVariables();
                }
                else
                {
                    size++;
                    fwdChecking(size);
                }
            }
        }
    }

    /*
     * FLA: Full Look Ahead
     */
    public static void fullLookAhead(int size)
    {
        Integer[] unassignedValues = null;
        int temp = 0;
        Integer[] domainVal = SolvingTechniques.createListInteger(domainValue.get(nodes[size]));
        Integer[] assignValues = null;
        int selectedValues = -1;
        boolean checkConstraints = true;
        if(!container.isEmpty() && container.containsKey(nodes[size]))
        {
            assignValues = SolvingTechniques.createIntegerList(container.get(nodes[size]));
        }
        if(assignValues != null && size == 0 && SolvingTechniques.checkArrays(domainVal, assignValues))
        {
            System.out.println("Sorry No Solution found. Try again.");
        }
        else
        {
            if(assignValues == null || assignValues.length < 1)
            {
                unassignedValues = domainVal;
            }
            else
            {
                unassignedValues = SolvingTechniques.checkItems(domainVal, assignValues);
            }
            if(size==0)
            {
                temp = SolvingTechniques.randomWithRange(0, unassignedValues.length-1);
                selectedValues = unassignedValues[temp];
                checkConstraints = false;
            }
            else
            {
                for(int i=0; i < unassignedValues.length; i++)
                {
                    if(checkConstraint(size, unassignedValues[i]) == false)
                    {
                        selectedValues = unassignedValues[i];
                        i = unassignedValues.length;
                        checkConstraints = false;
                    }
                }
            }
            arcConsistencyRun();
            if(checkConstraints == true)
            {
                if(container.containsKey(nodes[size]))
                {
                    container.remove(nodes[size]);
                }
                if(size > 0)
                {   size --; }
                fullLookAhead(size);
            }
            else
            {
                if(container.containsKey(nodes[size]))
                {
                    ArrayList<String> sv = container.get(nodes[size]);
                    sv.add(Integer.toString(selectedValues));
                    container.put(nodes[size], sv);
                }
                else
                {
                    ArrayList<String> sv = new ArrayList();
                    sv.add(Integer.toString(selectedValues));
                    container.put(nodes[size], sv);
                }
                if(size == (nodes.length-1))
                {
                    System.out.println("\n*****Printing the Output Values after searching.*****\n");
                    printVariables();
                }else
                {
                    size++;
                    fullLookAhead(size);
                }
            }
        }
    }

    /*
     * AC: Arc Consistency
     */
    public static void arcConsistencyRun()
    {
        Map<String, ArrayList<Integer>> domainList = new HashMap<String, ArrayList<Integer>>();
        Map<String, String> constraint = new HashMap<String, String>();
        constraint = constraints;
        Map<String, ArrayList<Integer>> temp = new HashMap<String, ArrayList<Integer>>();
        ArrayList<Integer> joinValues = new ArrayList();
        for(Map.Entry<String, String> entry : constraint.entrySet())
        {
            temp = acceptableValues(entry.getKey(), entry.getValue());
            if(!temp.isEmpty())
            {
                for (Map.Entry<String, ArrayList<Integer>> entryx : temp.entrySet())
                {
                    if(domainList.containsKey(entryx.getKey()))
                    {
                        domainList.put(entryx.getKey(), SolvingTechniques.joinList(entryx.getValue(), domainList.get(entryx.getKey())));
                    }
                    else
                    {
                        domainList.put(entryx.getKey(), entryx.getValue());
                    }
                }
                temp.clear();
            }
        }
        domainValue = domainList;
    }

    public static void forwardCheckAC(int size, int value)
    {
        String[] values_constraints;
        String[] pair_constraints;
        String tempCons="";
        String constraintValue = nodes[size];
        String checkedVariables="";
        for (Map.Entry<String, String> entry : constraints.entrySet())
        {
            if(entry.getKey().contains(constraintValue))
            {
                tempCons += entry.getValue(); tempCons += "-";
                checkedVariables = entry.getKey();
                checkedVariables += "-";
            }
        }
        checkedVariables = checkedVariables.substring(0,checkedVariables.length()-1);
        tempCons = tempCons.substring(0,tempCons.length()-1);
        if(!checkedVariables.isEmpty() && !tempCons.isEmpty())
        {
            pair_constraints = tempCons.split("-");
            values_constraints = checkedVariables.split("-");
        }
    }

    public static String checkNodes(String firstNode, String secondNode)
    {
        String temp3="";
        String temp="";
        String constraint="";
        String temp2="";
        String incompatible="none";
        int check=0;
        if(constraints.containsKey(firstNode+","+secondNode))
        {
            constraint = constraints.get(firstNode+","+secondNode);
        }
        temp = "";
        check=0;
        if(constraints.containsKey(secondNode+","+firstNode))
        {
            temp3 = constraints.get(secondNode+","+firstNode);
        }
        if(constraint.isEmpty())
        {
            incompatible = temp3;
        }
        else
        if(!constraint.isEmpty() && !temp3.isEmpty())
        {
            incompatible = constraint+"~"+temp3;
        }
        else
        {
            incompatible = constraint;
        }
        return incompatible;
    }

    public static boolean checkConstraint(int size, int value)
    {
        String assignedVals="";
        int rootNode=0;
        String reverse="";
        String temp="";
        boolean stateBool = true;
        String var="";
        ArrayList<String> valueArraylist;
        if(size == 0)
        {
            rootNode=0;
        }
        else
        {
            for(int i=size; i > 0; i-- )
            {
                reverse = getIncompatibleTuples(nodes[size], nodes[i-1]);
                if(!reverse.isEmpty())
                {
                    var = reverse+"~"+var;
                }
                if(container.containsKey(nodes[i-1]))
                {
                    valueArraylist = container.get(nodes[i-1]);
                    if(valueArraylist.size() > 0)
                    {
                        assignedVals += valueArraylist.get(valueArraylist.size()-1); assignedVals += "~";
                    }
                    else
                    {
                        assignedVals+= valueArraylist.get(0); assignedVals += "~";
                    }
                }
            }
            assignedVals = assignedVals.substring(0,assignedVals.length()-1);
            if(assignedVals.isEmpty())
            {
                rootNode = 0;
            }
            else
            {
                String[] chkAs = assignedVals.split("~");
                String cs="";
                if(chkAs != null && chkAs.length > 0)
                {
                    for(int i=0; i < chkAs.length; i++)
                    {
                        cs = value+","+chkAs[i];
                        if(var.contains(cs))
                        {
                            rootNode++;
                        }
                    }
                }
                else
                {
                    rootNode = 0;
                }
            }
        }
        if(rootNode < 1)
        {
            stateBool = false;
        }
        return stateBool;
    }

    public static ArrayList<String> desertedDomain(String temp, String val)
    {
        ArrayList<Integer> domainVal = new ArrayList();
        int status = -1;
        ArrayList<String> used= new ArrayList();
        if(domainValue.containsKey(temp))
        {
            domainVal = domainValue.get(temp);
            for(int i=0; i < domainVal.size(); i++)
            {
                status = domainVal.get(i);
                if(Integer.parseInt(val) != status)
                {
                    used.add(Integer.toString(status));
                }
            }
        }
        return used;
    }

    public static ArrayList<String> notInDomain(String var, String val)
    {
        ArrayList<String> res= new ArrayList();
        ArrayList<Integer> dvals = new ArrayList();
        int v=-1;
        if(domainValue.containsKey(var))
        {
            dvals = domainValue.get(var);
            for(int i=0; i < dvals.size(); i++)
            {
                v = dvals.get(i);
                if(Integer.parseInt(val) != v)
                {
                    res.add(Integer.toString(v));
                }
            }
        }
        return res;
    }

    /*
     * Finding Incompatible Tuples
     */
    public static String getIncompatibleTuples(String frstNode, String secNode)
    {
        String tmp3="";
        String tmp="";
        String constraint="";
        String tmp2="";
        String incompatibleTupl="none";
        int check=0;
        if(constraints.containsKey(frstNode+","+secNode))
        {
            constraint = constraints.get(frstNode+","+secNode);
        }
        tmp = "";
        check=0;
        if(constraints.containsKey(secNode+","+frstNode))
        {
            tmp3 = constraints.get(secNode+","+frstNode);
        }
        if(constraint.isEmpty())
        {
            incompatibleTupl = tmp3;
        }
        else
        if(!constraint.isEmpty() && !tmp3.isEmpty())
        {
            incompatibleTupl = constraint+"~"+tmp3;
        }
        else
        {
            incompatibleTupl = constraint;
        }
        return incompatibleTupl;
    }

    public static Map<String, ArrayList<Integer>> acceptableValues(String cons, String conIncompairs)
    {
        ArrayList<Integer> values1 = new ArrayList();
        String[] newString = conIncompairs.replace("~",",").split(",");
        Map<String, ArrayList<Integer>> reverse = new HashMap<String, ArrayList<Integer>>();
        String[] cs = cons.split(",");
        ArrayList<Integer> values2 = new ArrayList();
        ArrayList<String> temp = new ArrayList();
        if(newString.length > 0 && cs.length > 1)
        {
            String[] newString1 = new String[newString.length/2];
            String[] newString2 = new String[newString.length/2];
            for(int i=0; i < newString.length/2; i++)
            {
                newString1[i] = newString[(i*2)];
                newString2[i] = newString[(i*2)+1];
            }
            for(int i=0; i < newString1.length; i++)
            {
                temp = desertedDomain(cs[0],newString1[i]);
                if(!temp.isEmpty())
                {
                    for(int j=0; j < temp.size(); j++)
                    {
                        if(!values1.contains(Integer.parseInt(temp.get(j))))
                        {
                            values1.add(Integer.parseInt(temp.get(j)));
                        }
                    }
                }
                temp.clear();
            }
            for(int i=0; i < newString2.length; i++)
            {
                temp = desertedDomain(cs[1],newString2[i]);
                if(!temp.isEmpty())
                {
                    for(int j=0; j < temp.size(); j++)
                    {
                        if(!values2.contains(Integer.parseInt(temp.get(j))))
                        {
                            values2.add(Integer.parseInt(temp.get(j)));
                        }
                    }
                }
                temp.clear();
            }
            reverse.put(cs[0], values1);
            reverse.put(cs[1], values2);
        }
        return reverse;
    }

    public static ArrayList<String> CountValues(String temp, String val)
    {
        ArrayList<Integer> domainVal = new ArrayList();
        int status = -2;
        ArrayList<String> used= new ArrayList();
        if(domainValue.containsKey(temp))
        {
            domainVal = domainValue.get(temp);
            for(int i=0; i < domainVal.size(); i++)
            {
                status = domainVal.get(i);
                if(Integer.parseInt(val) != status)
                {
                    used.add(Integer.toString(status));
                }
            }
        }
        return used;
    }

    public static void printVariables()
    {
        if(container.isEmpty())
        {
            System.out.println("Error: No values assigned to Variables.");
        }
        else
        {
            for (Map.Entry<String, ArrayList<String>> item : container.entrySet())
            {
                System.out.println("ID: "+item.getKey()+" Value: "+item.getValue().get(0));
            }
        }
    }
}
