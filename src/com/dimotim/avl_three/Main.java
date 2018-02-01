package com.dimotim.avl_three;

import com.sun.istack.internal.NotNull;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        testInsertAndFindAndDelete();
    }

    public static void testInsertAndFindAndDelete(){
        AVLTree<Integer, Integer> tree=new AVLTree<>();
        Random random=new Random();
        final int size=1000;
        final int bound=2000;
        int[] numbers=new int[size];
        boolean[] used=new boolean[bound];


        for(int i=0;i<size;i++){
            int k;
            while (!used[(k=random.nextInt(bound))]){
                used[k]=true;
                numbers[i]=k;
                tree.insert(k,0);
            }
        }

        for(int i=0; i<size; i++){
            if(null==tree.find(numbers[i]))throw new RuntimeException();
        }

        for(int i=0;i<size; i++){
            if(i%2==0)tree.delete(numbers[i]);
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
        else root.insert(key, value);
        if(root!=null)root.heightTest();
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

        boolean isList(){
            return left==null && right==null;
        }

        Node<Key, Value> rotateL(){
            Node<Key, Value> newRoot=right;
            Node<Key, Value> middle=newRoot.left;
            right=middle;
            newRoot.left=this;
            return newRoot;
        }

        Node<Key, Value> rotareR(){
            Node<Key, Value> newRoot=left;
            Node<Key, Value> middle=newRoot.right;
            left=middle;
            newRoot.right=this;
            return newRoot;
        }

        @NotNull Node<Key, Value> closestL(Node<Key, Value> pred){
            if(right==null)return pred;
            else return right.closestL(this);
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
                @NotNull Node<Key, Value> closestLPred=left.closestL(this);
                Node<Key, Value> closestL=closestLPred.right;
                Node<Key, Value> closestsTree=closestL.left;
                closestL.left=left;
                closestL.right=right;
                closestLPred.right=closestsTree;
                closestLPred.updateHeight();
                closestL.updateHeight();
                return closestL;
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

        void insert(Key key, @NotNull Value value){
            if(this.key.compareTo(key)==0){
                this.key=key;
                return;
            }
            if(this.key.compareTo(key)>0) {
                if (left == null) left = new Node<>(key, value);
                else left.insert(key, value);
                updateHeight();
            }
            else {
                if(right==null)right=new Node<>(key,value);
                else right.insert(key, value);
                updateHeight();
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

