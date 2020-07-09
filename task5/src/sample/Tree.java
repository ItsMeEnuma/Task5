package sample;

import java.util.Stack;

public class Tree {
    private class TreeNode{
         TreeNode left;
         TreeNode right;
         double value;
         TreeNode parent;
         int index;

        public TreeNode(double value) {
            this.value = value;
        }
    }
    private TreeNode root;

    private void printNode(TreeNode node, int level){
        if(node != null){
            for(int i = 0; i < level; i++){
                System.out.print("\t");
            }
            System.out.println(node.value);
            printNode(node.left, level + 1);
            printNode(node.right, level + 1);
        }
    }

    public void load(String text){

        String[] strings = text.split("\n");

        root = new TreeNode(Double.parseDouble(strings[0]));
        root.index = 0;

        TreeNode node = root;
        int level = 0;

        for(int i = 1; i < strings.length; i++){
            String s = strings[i];

            int cursor = 0;
            int this_level = 0;

            while(cursor < s.length() && s.charAt(cursor) == '\t'){
                this_level++;
                cursor++;
            }

            s = s.substring(cursor);

            double this_value = Double.parseDouble(s);

            TreeNode new_node = new TreeNode(this_value);
            new_node.index = i;

            if(this_level <= level){
                while (level != this_level){
                    node = node.parent;
                    level--;
                }

                if(node.parent.left == null){
                    node.parent.left = new_node;
                    new_node.parent = node.parent;
                    node = new_node;
                }
                else if(node.parent.right == null){
                    node.parent.right = new_node;
                    new_node.parent = node.parent;
                    node = new_node;
                }
                else{
                    throw new Error();
                }
            }

            if(this_level > level){
                new_node.parent = node;
                new_node.index = i;

                level += 1;

                if(node.left == null){
                    node.left = new_node;
                    node = new_node;
                }
                else if(node.right == null){
                    node.right = new_node;
                    node = new_node;
                }
                else{
                    throw new Error();
                }
            }
        }
    }

    public void print (){
        printNode(root, 0);
    }

    public String save(){
        Stack<TreeNode> nodes = new Stack<>();
        if(root != null)
            nodes.add(root);

        StringBuilder code = new StringBuilder();
        code.append("digraph G {\n");

        while(!nodes.empty()) {
            TreeNode node = nodes.pop();
            if (node.right != null) nodes.push(node.right);
            if (node.left != null) nodes.push(node.left);
            code.append("  ");
            code.append(node.index);
            code.append(" [label=\"");
            code.append(node.value);
            code.append("\"]");
            code.append("\n");
        }

        if(root != null)
            nodes.add(root);

        while(!nodes.empty()){
            TreeNode node = nodes.pop();
            if(node.right != null) nodes.push(node.right);
            if(node.left != null) nodes.push(node.left);
            if(node.parent != null){
                code.append("  ");
                code.append(node.parent.index);
                code.append(" -> ");
                code.append(node.index);
                code.append(";\n");
            }
        }
        code.append("}\n");
        return code.toString();
    }

    public double min(){
        if(root == null)
            throw new Error();

        Stack<TreeNode> nodes = new Stack<>();
        nodes.add(root);

        double res = root.value;

        while(!nodes.isEmpty()){
            TreeNode currect = nodes.pop();
            if(currect.value  < res){
                res = currect.value;
            }
            if(currect.left != null)
                nodes.push(currect.left);

            if(currect.right != null)
                nodes.push(currect.right);
        }
        return res;
    }
}