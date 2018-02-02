package com.dimotim.avl_three;

import com.sun.istack.internal.NotNull;

public class AVLTree<Key extends Comparable<Key>, Value> {
    Node<Key, Value> root;

    public void insert(Key key, Value value){
        if(root==null)root=new Node<>(key, value);
        else root=root.insert(key, value);
    }

    public Value find(Key key){
        if(root==null)return null;
        else return root.find(key);
    }

    public void delete(Key key){
        if (root != null) root=root.delete(key);
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

        static Node[] deleteClosest(Node left){
            if(left.right.right==null){
                Node closest=left.right;
                left.right=closest.left;
                return new Node[]{closest,normalize(left)};
            }
            else{
                Node[] ret=deleteClosest(left.right);
                Node closest=ret[0];
                Node newRight=ret[1];
                left.right=newRight;
                return new Node[]{closest,normalize(left)};
            }
        }

        Node<Key, Value> delete(Key key){
            if(this.key.compareTo(key)==0){
                if(right==null)return left;
                if(left==null)return right;
                if(left.right==null){
                    left.right=right;
                    return normalize(left);
                }
                Node[] ret=deleteClosest(left);
                Node newRoot=ret[0];
                Node newLeft=ret[1];
                newRoot.left=newLeft;
                newRoot.right=right;
                return normalize(newRoot);
            }
            if(this.key.compareTo(key)>0){
                if(left!=null)left=left.delete(key);
                return normalize(this);
            }
            else {
                if (right != null) right = right.delete(key);
                return normalize(this);
            }
        }

        static Node normalize(Node root){
            if(root.leftHeight()-root.rightHeight()>1){
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
