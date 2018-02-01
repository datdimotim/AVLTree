package com.dimotim.avl_three;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //while (true)testInsertAndFindAndDelete();
        while (true)testInsertAndFindAndAVL();
    }

    public static void testInsertAndFindAndAVL(){
        AVLTree<Integer, Integer> tree=new AVLTree<>();
        Random random=new Random();
        final int size=1000;
        final int bound=2000;
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
        final int size=1000;
        final int bound=2000;
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
        }

        for(int i=0; i<size; i++){
            if(null==tree.find(numbers[i]))throw new RuntimeException();
        }

        for(int i=0;i<size; i++){
            try {
                if(i%2==0)tree.delete(numbers[i]);
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
}

class AVLTree<Key extends Comparable<Key>, Value> {
    Node<Key, Value> root;

    public void insert(Key key, Value value){
        if(root==null)root=new Node<>(key, value);
        else root=root.insert(key, value);
        if(root!=null){
            root.heightTest();
            root.testAVL();
        }
    }

    public Value find(Key key){
        if(root==null)return null;
        else return root.find(key);
    }

    public void delete(Key key){
        if(root==null)throw new IllegalStateException("tree is empty!");
        else root=root.delete(key);
        if(root!=null)root.heightTest();
    }

    static class Node<Key extends Comparable<Key>, Value> {
        int height;
        Key key;
        Value value;
        Node<Key, Value> left;
        Node<Key, Value> right;

        Node(Key val, Value value) {
            this.key =val;
            this.value=value;
            left=null;
            right=null;
            height=1;
        }

        int rightHeight(){
            if(right==null)return 0;
            else return right.height;
        }

        int leftHeight(){
            if(left==null)return 0;
            else return left.height;
        }

        void updateHeight(){
            height = 1+Math.max(leftHeight(),rightHeight());
        }

        void heightTest(){
            if(height!=testHeight())throw new RuntimeException();
            if(left!=null)left.heightTest();
            if(right!=null)right.heightTest();
        }

        int testHeight(){
            if(isList())return 1;
            if(left==null)return 1+right.testHeight();
            if(right==null)return 1+left.testHeight();
            return 1+Math.max(left.testHeight(),right.testHeight());
        }

        void testAVL(){
            if(left!=null)left.testAVL();
            if(right!=null)right.testAVL();
            if(Math.abs(leftHeight()-rightHeight())>1)throw new RuntimeException();
        }

        boolean isList(){
            return left==null && right==null;
        }

        Node<Key, Value> rotateL(){
            Node<Key, Value> newRoot=right;
            Node<Key, Value> middle=newRoot.left;
            right=middle;
            newRoot.left=this;
            updateHeight();
            newRoot.updateHeight();
            return newRoot;
        }

        Node<Key, Value> rotateR(){
            Node<Key, Value> newRoot=left;
            Node<Key, Value> middle=newRoot.right;
            left=middle;
            newRoot.right=this;
            updateHeight();
            newRoot.updateHeight();
            return newRoot;
        }

        @NotNull Node<Key, Value> closestL(Node<Key, Value> pred, Node<Key, Value> root){
            if(right==null){
                deleteClosestLHelper(pred,root);
                return this;
            }
            else {
                Node<Key,Value> res=right.closestL(this,root);
                updateHeight();
                return res;
            }
        }

        static void deleteClosestLHelper(Node predClosest, Node root){
            Node closestL=predClosest.right;
            Node closestsTree=closestL.left;
            closestL.left=root.left;
            closestL.right=root.right;
            predClosest.right=closestsTree;
            predClosest.updateHeight();
        }

        Node<Key, Value> delete(Key key){
            if(this.key.compareTo(key)==0){
                if(right==null)return left;
                if(left==null)return right;
                if(left.right==null){
                    left.right=right;
                    left.updateHeight();
                    return left;
                }
                Node<Key,Value> newRoot=left.closestL(this,this);
                newRoot.updateHeight();
                return newRoot;
            }
            if(this.key.compareTo(key)>0){
                if(left!=null)left=left.delete(key);
                updateHeight();
                return this;
            }
            else {
                if (right != null) right = right.delete(key);
                updateHeight();
                return this;
            }
        }

        static Node normalize(Node root){
            if(root.leftHeight()-root.leftHeight()>1){
                if(root.left.rightHeight()>root.left.leftHeight()){
                    root.left=root.left.rotateL();
                    return root.rotateR();
                }
                else return root.rotateR();
            }
            if(root.rightHeight()-root.leftHeight()>1){
                if(root.right.leftHeight()>root.right.rightHeight()){
                    root.right=root.right.rotateR();
                    return root.rotateL();
                }
                else return root.rotateL();
            }
            root.updateHeight();
            return root;
        }

        Node<Key,Value> insert(Key key, @NotNull Value value){
            if(this.key.compareTo(key)==0){
                this.key=key;
                return this;
            }
            if(this.key.compareTo(key)>0) {
                if (left == null){
                    left = new Node<>(key, value);
                    return normalize(this);
                }
                else {
                    left=left.insert(key, value);
                    if(leftHeight()-rightHeight()>1){
                        if(left.rightHeight()>left.leftHeight()){
                            left=left.rotateL();
                            return rotateR();
                        }
                        else return rotateR();
                    }
                    else {
                        return normalize(this);
                    }
                }
            }
            else {
                if(right==null){
                    right=new Node<>(key,value);
                    return normalize(this);
                }
                else right=right.insert(key, value);
                return normalize(this);
            }
        }

        Value find(Key key){
            if(this.key.compareTo(key)==0)return value;
            if(this.key.compareTo(key)>0){
                if(left==null)return null;
                else return left.find(key);
            }
            else {
                if(right==null)return null;
                else return right.find(key);
            }
        }
    }
}

