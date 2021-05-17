# Desafio
[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.4.31-blue.svg)](https://kotlinlang.org)
[![Gradle](https://img.shields.io/badge/Gradle-6.5-blue?style=flat)](https://gradle.org)

# Características do Projeto
* 100% [Kotlin](https://kotlinlang.org/)
* Clean Architecture (modularizado por feature e camadas), para permitir que o app reaja bem a mudanças.
* MVVM (Model-View-ViewModel)
* Navigation Component
* [Android Jetpack](https://developer.android.com/jetpack)
* CI pipeline ([GitHub Actions](https://github.com/features/actions))
* Testes Unitários
* Testes Instrumentados
* Injeção de dependência (Koin)
* GIT Flow

# Arquitetura

A divisão dos módulos:
* app - contém uma classe Application que serve de base para o contexto do DI(Koin);

* agenda - possui a feature lista de contatos;

* data - possui toda a parte de dados locais e remotos da aplicação, assim como as conversões e lógica entre eles;

* domain - módulo que contém as regras de negócio da aplicação. É puramente Kotlin e como está sendo utilizada a Clean Architecture, possui Casos de Uso;

* base - contém classes de base para o projeto;

* buildSrc - módulo que contém todas as dependências e versões do projeto, incluindo seus módulos;

* sharedComponents - possui os componentes e dependências que podem ser compartilhados com o resto do app.

---------------------
# PicPay - Desafio Android

<img src="https://github.com/mobilepicpay/desafio-android/blob/master/desafio-picpay.gif" width="300"/>

Um dos desafios de qualquer time de desenvolvimento é lidar com código legado e no PicPay isso não é diferente. Um dos objetivos de trazer os melhores desenvolvedores do Brasil é atacar o problema. Para isso, essa etapa do processo consiste numa proposta de solução para o desafio abaixo e você pode escolher a melhor forma de resolvê-lo, de acordo com sua comodidade e disponibilidade de tempo:
- Resolver o desafio previamente, e explicar sua abordagem no momento da entrevista.
- Resolver o desafio durante a entrevista, fazendo um pair programming interativo com os nossos devs, guiando o desenvolvimento.

Com o passar do tempo identificamos alguns problemas que impedem esse aplicativo de escalar e acarretam problemas de experiência do usuário. A partir disso elaboramos a seguinte lista de requisitos que devem ser cumpridos ao melhorar nossa arquitetura:

- Em mudanças de configuração o aplicativo perde o estado da tela. Gostaríamos que o mesmo fosse mantido.
- Nossos relatórios de crash têm mostrado alguns crashes relacionados a campos que não deveriam ser nulos sendo nulos e gerenciamento de lifecycle. Gostaríamos que fossem corrigidos.
- Gostaríamos de cachear os dados retornados pelo servidor.
- Haverá mudanças na lógica de negócios e gostaríamos que a arquitetura reaja bem a isso.
- Haverá mudanças na lógica de apresentação. Gostaríamos que a arquitetura reaja bem a isso.
- Com um grande número de desenvolvedores e uma quantidade grande de mudanças ocorrendo testes automatizados são essenciais.
  - Gostaríamos de ter testes unitários testando nossa lógica de apresentação, negócios e dados independentemente, visto que tanto a escrita quanto execução dos mesmos são rápidas.
  - Por outro lado, testes unitários rodam em um ambiente de execução diferenciado e são menos fiéis ao dia-a-dia de nossos usuários, então testes instrumentados também são importantes.

Boa sorte! =)
