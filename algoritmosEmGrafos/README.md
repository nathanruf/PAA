# Implementação de Algoritmos em Grafos

## Descrição do Projeto

Este projeto consiste na implementação de um programa que oferece uma variedade de algoritmos em grafos. O usuário pode carregar um grafo a partir de um arquivo de texto no formato padrão (`teste.txt`). 

Algumas classes de auxílio para construção do grafo foram disponibilizadas pelo professor.

## Recursos Disponíveis

- Interface gráfica para facilitar a interação.
- O usuário pode escolher entre três estruturas de dados para armazenar o grafo: matriz de adjacência, matriz de incidência ou lista de adjacência.
- O usuário pode selecionar qual algoritmo de grafos deseja executar:
  - Busca em Profundidade (DFS).
  - Busca em Largura (BFS).
  - Árvore Geradora Mínima (AGM).
  - Caminho Mínimo entre dois vértices.
  - Algoritmo de Fluxo Máximo.

## Padrão de Entrada

O arquivo de entrada segue o seguinte padrão:

- A primeira linha contém um número inteiro, representando o número de vértices no grafo.
- Cada linha subsequente representa um vértice, onde:
  - O primeiro número é o índice do vértice.
  - Os pares subsequentes representam as arestas conectadas ao vértice no formato `vértice-valor`, separados por ponto e vírgula (`;`).
