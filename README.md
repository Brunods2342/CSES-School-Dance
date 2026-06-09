
# Trabalho Prático 3 - Unidade 3

## CSES School Dance
Foco: emparelhamento bipartido como rede de fluxo com capacidades unitárias

[link do problema](https://cses.fi/problemset/task/1696)

O problema consiste em encontrar o maior número possível de pares entre meninos e meninas para uma dança escolar.
Cada menino pode dançar com no máximo uma menina e cada menina pode dançar com no máximo um menino.
São fornecidos apenas os pares permitidos e o objetivo é determinar o emparelhamento máximo possível.

## Integrantes 
- Evandro Nobre 
- Tiago Goes
- Bruno Cavalcante
- Emanuel Alves


## Linguagem
`java` - sem dependências


## Execução
clonagem do repositório
```text
git clone https://github.com/Brunods2342/CSES-School-Dance.git
cd CSES-School-Dance
```

compilar
```text
javac src/Main.java -d bin 
```

executar
```text
java -cp bin Main < dados/input.txt
```

## Explicação da modelagem como rede de fluxo 

Dado o Grafo ponderado e direcionado G = (E,V), onde
`vértices` - meninos e meninas
    `orgiem` - liga a cada um dos meninos
    `sorvedouro` - é ligado à cada uma das meninas

`arestas` - possibilidade de formação de pares entre meninos e meninas

`pesos` - sempre com capacidade de 1, a criação de um par entre menino e menina

podemos modelar em um rede de fluxo da seguinte forma

![](/acompanhamento/grafo-fluxo.png)


## Algoritmo de Edmonds-Karp

O algoritmo é uma implementação do método Ford-Fulkerson que 
utiliza BFS para encontrar caminhos aumentantes no grafo residual. 
Sendo usado no lugar do Ford-Fulkerson, por conseguir lidar com capacidades inracionais.

passo-a-passo
1. Encontrar um caminho aumentante usando BFS
2. Calcular o gargalo do caminho.
3. Atualizar capacidades residuais.
4. Repetir enquanto existir caminho aumentante.

Quando não existir mais caminho da fonte ao sorvedouro, o fluxo máximo foi encontrado.

O grafo residual é mantido através de arestas reversas.
Sempre que uma unidade de fluxo passa por uma aresta:

`u → v`

a capacidade residual diminui e a aresta reversa:

`v → u`

recebe capacidade positiva.

Isso permite desfazer decisões anteriores e encontrar emparelhamentos melhores


## Conversão do Fluxo em Resposta

Após calcular o fluxo máximo, percorremos as arestas dos vértices dos meninos.

Se uma aresta:

`menino → menina`

ficou com capacidade residual igual a zero, significa que uma unidade de fluxo passou por ela.

Logo:

`capacidade residual` = 0

indica um par pertencente ao emparelhamento máximo.

Esses pares são impressos na saída.


## Correção da Solução

Cada caminho aumentante possui formato:

`Origem → Menino → Menina → Sorvedouro`

Uma unidade de fluxo corresponde exatamente a um par válido.

Como todas as capacidades são iguais a 1:

- nenhum menino pode ser usado mais de uma vez;
- nenhuma menina pode ser usada mais de uma vez.

Portanto:

Fluxo Máximo = Emparelhamento Máximo

## Complexidade

Considere:

- V = número de vértices
- E = número de arestas

O algoritmo Edmonds-Karp possui complexidade:

`O(V*E^2)`

Complexidade de entrada de dados:

V = `O(M+N+2)`

E = `O(M+N+K)`

# Casos especiais

- Nenhum par possível
- Todos podem dançar com todos

# Submissão ao CSES
![](/evidencias/image.png)





