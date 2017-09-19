import java.io.IOException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

// https://www.ibm.com/developerworks/cn/analytics/library/ba-1607-clustering-algorithm/index.html#5层次聚类算法详解及实现outline
public class Hierarchical {

    private double[][] matrix;

    public static class Node {
        String id;
        String text;
        SimHash simHash;
    }

    ArrayList<Node> nodes;

    public Hierarchical(ArrayList<Node> _nodes) throws IOException {
        nodes = _nodes;
        matrix = new double[nodes.size()][nodes.size()];

        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = i + 1; j < nodes.size(); ++j) {
                // System.out.println("i j:" + i + j);
                matrix[i][j] = nodes.get(i).simHash.hammingDistance(nodes.get(j).simHash);
            }
        }

        this.matrix = matrix;
    }

    private class Model {
        int x = 0;
        String xid;
        int y = 0;
        String yid;
        double value = 0;
    }

    private Model minModel = new Model();

    private Model findMinValueOfMatrix(double[][] matrix) { // 找出矩阵中距离最近的两个簇
        Model model = new Model();
        double min = 0x7fffffff;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = i + 1; j < matrix.length; ++j) {
                if (min > matrix[i][j] && matrix[i][j] != 0) {
                    min = matrix[i][j];
                    model.x = i;
                    model.xid = nodes.get(i).id;
                    model.y = j;
                    model.yid = nodes.get(j).id;
                    model.value = matrix[i][j];
                }
            }
        }
        return model;
    }

    ArrayList<ArrayList<Node>> assembles;

    public ArrayList<ArrayList<Node>> processHierarchical(int distance) {
        assembles = new ArrayList<>();

        try {
            while (true) { // 凝聚层次聚类迭代
                minModel = findMinValueOfMatrix(matrix);
                if (minModel.value == 0 || minModel.value > distance) { // 当找不出距离最近的两个簇时，迭代结束
                    break;
                }

                Boolean added = false;
                for (ArrayList<Node> assemble: assembles) {
                    if (added) break;
                    for (Node node: assemble) {
                        if (node.id == minModel.xid) {
                            assemble.add(nodes.get(minModel.y));
                            added = true;
                            break;
                        } else if (node.id == minModel.yid) {
                            assemble.add(nodes.get(minModel.x));
                            added = true;
                            break;
                        }
                    }
                }

                if (!added) {
                    ArrayList<Node> assemble = new ArrayList<Node>();
                    assemble.add(nodes.get(minModel.x));
                    assemble.add(nodes.get(minModel.y));
                    assembles.add(assemble);
                }

                // 更新矩阵
                matrix[minModel.x][minModel.y] = 0;
                for (int i = 0; i < matrix.length; ++i) { // 如果合并了点 p1 与 p2，则只保留 p1,p2 其中之一与其他点的距离，取较小值
                    if (matrix[i][minModel.x] <= matrix[i][minModel.y]) {
                        matrix[i][minModel.y] = 0;
                    } else {
                        matrix[i][minModel.x] = 0;
                    }
                    if (matrix[minModel.x][i] <= matrix[minModel.y][i]) {
                        matrix[minModel.y][i] = 0;
                    } else {
                        matrix[minModel.x][i] = 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return assembles;
    }
}