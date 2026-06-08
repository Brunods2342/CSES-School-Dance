import java.io.*;
import java.util.*;

public class Main {

    static class FastScanner {

        private final BufferedInputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = new BufferedInputStream(is);
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;

                if (len <= 0)
                    return -1;
            }

            return buffer[ptr++];
        }

        int nextInt() throws IOException {

            int c;

            do {
                c = read();
            } while (c <= ' ');

            int value = 0;

            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }

            return value;
        }
    }

    static class Edge {
        int to;
        int rev;
        int cap;

        Edge(int to, int rev, int cap) {
            this.to = to;
            this.rev = rev;
            this.cap = cap;
        }
    }

    static ArrayList<Edge>[] graph;

    static void addEdge(int u, int v, int cap) {
        Edge forward = new Edge(v, graph[v].size(), cap);
        Edge backward = new Edge(u, graph[u].size(), 0);

        graph[u].add(forward);
        graph[v].add(backward);
    }

    static int maxFlow(int source, int sink, int V) {

        int flow = 0;

        while (true) {

            int[] parent = new int[V];
            int[] parentEdge = new int[V];

            Arrays.fill(parent, -1);

            Queue<Integer> q = new ArrayDeque<>();
            q.add(source);

            parent[source] = source;

            while (!q.isEmpty() && parent[sink] == -1) {

                int u = q.poll();

                for (int i = 0; i < graph[u].size(); i++) {

                    Edge e = graph[u].get(i);

                    if (parent[e.to] == -1 && e.cap > 0) {

                        parent[e.to] = u;
                        parentEdge[e.to] = i;

                        q.add(e.to);
                    }
                }
            }

            if (parent[sink] == -1)
                break;

            int aug = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v]) {
                Edge e = graph[parent[v]].get(parentEdge[v]);
                aug = Math.min(aug, e.cap);
            }

            for (int v = sink; v != source; v = parent[v]) {
                Edge e = graph[parent[v]].get(parentEdge[v]);

                e.cap -= aug;
                graph[v].get(e.rev).cap += aug;
            }

            flow += aug;
        }

        return flow;
    }

    public static void main(String[] args) throws Exception {

        InputStream is;

        try {
            // TESTE LOCAL
            is = new FileInputStream("dados\\input.txt");
        } catch (Exception e) {
            // CSES / Juiz Online
            is = System.in;
        }

        FastScanner fs = new FastScanner(is);

        int n = fs.nextInt();
        int m = fs.nextInt();
        int k = fs.nextInt();

        int source = 0;
        int sink = n + m + 1;
        int V = sink + 1;

        graph = new ArrayList[V];

        for (int i = 0; i < V; i++) {
            graph[i] = new ArrayList<>();
        }

        // Source -> Meninos
        for (int boy = 1; boy <= n; boy++) {
            addEdge(source, boy, 1);
        }

        // Menino -> Menina
        for (int i = 0; i < k; i++) {

            int boy = fs.nextInt();
            int girl = fs.nextInt();

            addEdge(boy, n + girl, 1);
        }

        // Menina -> Sink
        for (int girl = 1; girl <= m; girl++) {
            addEdge(n + girl, sink, 1);
        }

        int matching = maxFlow(source, sink, V);

        StringBuilder out = new StringBuilder();

        out.append(matching).append('\n');

        // Recupera os pares do matching
        for (int boy = 1; boy <= n; boy++) {

            for (Edge e : graph[boy]) {

                if (e.to > n &&
                    e.to <= n + m &&
                    e.cap == 0) {

                    int girl = e.to - n;

                    out.append(boy)
                       .append(' ')
                       .append(girl)
                       .append('\n');
                }
            }
        }

        System.out.print(out);
    }
}