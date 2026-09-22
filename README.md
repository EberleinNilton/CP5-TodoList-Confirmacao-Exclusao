# CP5 — To-Do List com confirmação de exclusão

Projeto Android em **Kotlin + Jetpack Compose Material 3**, organizado em **MVVM**, desenvolvido para a prova prática de Android Kotlin Developer.

## Funcionalidades

- Cadastro de tarefas
- Edição de tarefas
- Conclusão e reabertura de tarefas
- Prazo por data
- Indicação visual de tarefa atrasada
- Ordenação por criação, prazo, título e status
- Exclusão somente após confirmação
- Diálogo com o título da tarefa selecionada
- Ação **Cancelar** mantém a tarefa na lista
- Ação **Excluir** remove somente a tarefa selecionada
- `@Preview` da lista e do estado de confirmação de exclusão

## Arquitetura

- `model/`: modelos de domínio
- `data/`: repositório
- `viewmodel/`: estado e regras de apresentação
- `ui/`: telas, componentes e tema

## Como executar

1. Abra a pasta do projeto no Android Studio.
2. Aguarde o Gradle Sync.
3. Selecione um emulador Android API 26 ou superior.
4. Execute o módulo `app`.

## Evidências da prova

As cinco capturas obrigatórias estão documentadas em [`EVIDENCIAS_EXCLUSAO.md`](EVIDENCIAS_EXCLUSAO.md) e armazenadas em `docs/images/exclusao/`.
