package com.dimotim.avl_three;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //for(int i=0;i<100000;i++)testInsertAndFindAndDelete();
        //for(int i=0;i<100000;i++)testInsertAndFindAndAVL();
        while (true)benchmark();
    }

    public static void testInsertAndFindAndAVL(){
        AVLTree<Integer, Integer> tree=new AVLTree<>();
        Random random=new Random();
        final int size=32;
        final int bound=64;
        int[] numbers=new int[size];
        boolean[] used=new boolean[bound];

        List<Integer> testSet=new ArrayList<>();

        for(int i=0;i<size;i++){
            int k;
            while (used[(k=random.nextInt(bound))]);
            testSet.add(k);
            used[k]=true;
            numbers[i]=k;
            tree.insert(k,0);
            NodeTestsUtills.testTree(tree.root);
        }

        for(int i=0; i<size; i++){
            try {
                if(null==tree.find(numbers[i]))throw new RuntimeException();
            }
            catch (RuntimeException e){
                new Viewer(tree);
                System.out.println("find="+numbers[i]);
                System.out.println(testSet);
                throw new RuntimeException(e);
            }
        }
    }
    public static void testInsertAndFindAndDelete(){
        AVLTree<Integer, Integer> tree=new AVLTree<>();
        Random random=new Random();
        final int size=32;
        final int bound=64;
        int[] numbers=new int[size];
        boolean[] used=new boolean[bound];

        List<Integer> testSet=new ArrayList<>();

        for(int i=0;i<size;i++){
            try{

            int k;
            while (used[(k=random.nextInt(bound))]);
            testSet.add(k);
            used[k]=true;
            numbers[i]=k;
            tree.insert(k,0);
            NodeTestsUtills.testTree(tree.root);
            }
            catch (RuntimeException e){
                System.out.println(testSet);
                new Viewer(tree);
                throw new RuntimeException(e);
            }
        }

        for(int i=0; i<size; i++){
            if(null==tree.find(numbers[i]))throw new RuntimeException();
        }

        for(int i=0;i<size; i++){
            try {
                if(i%2==0)tree.delete(numbers[i]);
                NodeTestsUtills.testTree(tree.root);
            }
            catch (RuntimeException e){
                new Viewer(tree);
                System.out.println(testSet);
                throw new RuntimeException(e);
            }
        }

        for(int i=0;i<size; i++){
            if(i%2==0)if(null!=tree.find(numbers[i]))throw new RuntimeException();
            if(i%2==1)if(null==tree.find(numbers[i])) throw new RuntimeException();
        }
    }

    public static void benchmark(){
        Random random=new Random();
        final int size=1000000;
        final int bound=2*size;
        int[] numbers=new int[size];
        for(int i=0;i<size;i++)numbers[i]=random.nextInt(bound);

        AVLTree<Integer, Integer> avl=new AVLTree<>();
        final long startInsertAVL=System.currentTimeMillis();
        for (int n:numbers)avl.insert(n,n);
        System.out.print("insert to avl/tm: "+(System.currentTimeMillis()-startInsertAVL));

        final long startInsertTM=System.currentTimeMillis();
        Map<Integer, Integer> tm=new TreeMap<>();
        for (int n:numbers)tm.put(n,n);
        System.out.println("\t"+(System.currentTimeMillis()-startInsertTM));

        final long startFindAVL=System.currentTimeMillis();
        for (int n:numbers)if(!avl.find(n).equals(n))throw new RuntimeException();
        System.out.print("find in avl/tm  : "+(System.currentTimeMillis()-startFindAVL));

        final long startFindTM=System.currentTimeMillis();
        for (int n:numbers)if(!tm.get(n).equals(n))throw new RuntimeException();
        System.out.println("\t"+(System.currentTimeMillis()-startFindTM));

        final long startDelAVL=System.currentTimeMillis();
        for (int n:numbers)avl.delete(n);
        System.out.print("remove in avl/tm: "+(System.currentTimeMillis()-startDelAVL));

        final long startDelTM=System.currentTimeMillis();
        for (int n:numbers)tm.remove(n);
        System.out.println("\t"+(System.currentTimeMillis()-startDelTM));
        System.out.println();
    }
}

class NodeTestsUtills{
    static void testTree(AVLTree.Node root){
        heightTest(root);
        testAVL(root);
    }
    private static void heightTest(AVLTree.Node node){
        if (node==null)return;
        if(node.height!=testHeight(node))throw new RuntimeException();
        if(node.left!=null)heightTest(node.left);
        if(node.right!=null)heightTest(node.right);
    }

    private static int testHeight(AVLTree.Node node){
        if(node==null)return 0;
        if(isList(node))return 1;
        if(node.left==null)return 1+testHeight(node.right);
        if(node.right==null)return 1+testHeight(node.left);
        return 1+Math.max(testHeight(node.left),testHeight(node.right));
    }

    private static void testAVL(AVLTree.Node node){
        if(node==null)return;
        if(node.left!=null)testAVL(node.left);
        if(node.right!=null)testAVL(node.right);
        if(Math.abs(node.leftHeight()-node.rightHeight())>1)throw new RuntimeException();
    }
    private static boolean isList(AVLTree.Node node){
        return node.left==null && node.right==null;
    }
}

