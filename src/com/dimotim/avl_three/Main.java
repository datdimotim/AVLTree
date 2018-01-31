package com.dimotim.avl_three;

import com.sun.istack.internal.NotNull;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        while (true)testInsertAndFindAndDelete();
    }

    public static void testInsertAndFindAndDelete(){
        AVLTree<Integer, Integer> tree=new AVLTree<>();
        Random random=new Random();
        final int size=1000;
        final int bound=2000;
        int[] numbers=new int[size];

        int state=0;

        for(int i=0;i<size;i++){
            state+=random.nextInt(bound)+1;
            numbers[i]=state;
            tree.insert(state,0);
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
    private Node<Key, Value> root;

    public void insert(Key key, Value value){
        if(root==null)root=new Node<>(key, value);
        else root.insert(key, value);
    }

    public Value find(Key key){
        if(root==null)return null;
        else return root.find(key);
    }

    public void delete(Key key){
        if(root==null)throw new IllegalStateException("tree is empty!");
        else root=root.delete(key);
    }

    private static class Node<Key extends Comparable<Key>, Value> {
        int balance;
        Key key;
        Value value;
        Node<Key, Value> left;
        Node<Key, Value> right;

        Node(Key val, Value value) {
            this.key =val;
            this.value=value;
            left=null;
            right=null;
            balance=0;
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
                    return left;
                }
                @NotNull Node<Key, Value> closestLPred=left.closestL(this);
                Node<Key, Value> closestL=closestLPred.right;
                Node<Key, Value> closestsTree=closestL.left;
                closestL.left=left;
                closestL.right=right;
                closestLPred.right=closestsTree;
                return closestL;
            }
            if(this.key.compareTo(key)<0){
                if(left!=null)left=left.delete(key);
                return this;
            }
            else {
                if (right != null) right = right.delete(key);
                return this;
            }
        }

        void insert(Key key, @NotNull Value value){
            if(this.key.compareTo(key)==0){
                this.key =key;
                return;
            }
            if(this.key.compareTo(key)<0) {
                if (left == null) left = new Node<>(key, value);
                else left.insert(key, value);
            }
            else {
                if(right==null)right=new Node<>(key,value);
                else right.insert(key, value);
            }
        }

        Value find(Key key){
            if(this.key.compareTo(key)==0)return value;
            if(this.key.compareTo(key)<0){
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

