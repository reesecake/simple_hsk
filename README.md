# [Simple HSK](http://simplehsk.com)
[![CircleCI](https://circleci.com/gh/reesecake/simple_hsk.svg?style=shield&circle-token=998629975402253a8b54782911e224a7a7906729)](https://circleci.com/gh/reesecake/simple_hsk)
[![codecov](https://codecov.io/gh/reesecake/simple_hsk/branch/master/graph/badge.svg?token=6CD1BF0W4B)](https://codecov.io/gh/reesecake/simple_hsk)
![Heroku](https://pyheroku-badge.herokuapp.com/?app=simplehsk&style=flat)

> The Hanyu Shuiping Kaoshi (HSK; Chinese: 汉语水平考试; pinyin: Hànyǔ Shuǐpíng Kǎoshì), translated as the Chinese Proficiency Test, is the standardized test of Standard Chinese (a type of Mandarin Chinese) language proficiency of China for non-native speakers such as foreign students and overseas Chinese. The test is administered by the Hanban, an agency of the Ministry of Education of the People's Republic of China.
>
> &mdash; [Wikipedia](https://en.wikipedia.org/wiki/Hanyu_Shuiping_Kaoshi)

This project aims to 'simplify' learning with the HSK tests by hosting the vocabulary lists of each level of the HSK 
tests. Each level approximately doubles the number of new words from the previous test; the purpose of simple_hsk is 
to allow each test's vocabulary list to be viewed _cumulatively_ and _non-cumulatively_.

---

## Implementation
### Backend
#### Spring Boot REST API
The Spring Boot web server provides a REST API of `Vocab` objects in `hal+json` form. It is accessible from the mapping: `/api/**` and uses Spring Data REST for the basic Controller and JPA Repository implementation.

#### PostgreSQL Database
Application data is retrieved from a PostgreSQL database connected via Spring Data JPA and the Vocab data model. 
Heroku provides a _Heroku Postgres_ resource add-on which contains all 5000 HSK vocabulary words.

#### H2 in-memory Database
The project has a Maven dependency for an H2 database in testing cases only. Using the `application-test.properties`, all Spring tests run with the `test` profile and do not make any changes to the local or production PostgreSQL databases.

### Frontend
#### React
Most of the site's interactability is built with React, including the HSK Vocab lists and quizzes.

Vocab lists are populated with calls to the REST API and the React components work together to provide pagination, sorting, etc.

HSK Quizzes are built with a number of React components to randomly serve vocabulary quizzes based on settings editable to the user. For example, users can edit the number of questions in a quiz, ranging from 0 to the number of `Vocab`s returned in the API call. All quizzes are submittable and the `RadioGroup`s within the `Question` components are `disabled` so users cannot edit and resubmit their quiz. Quiz results are displayed with a score counter and questions are re-styled with green or red, indicating correctness (or incorrectness).

#### Thymeleaf
Thymeleaf is a Spring-integrated HTML5 templating engine run by the Spring web server. Thymeleaf is used here for HTML templates returned by Spring `GET` requests for most mappings and also for [_Context-relative URLs_](https://www.thymeleaf.org/doc/articles/standardurlsyntax.html) and its fragmenting [_layout_](https://www.thymeleaf.org/doc/articles/layouts.html) functionality.

---

## Testing
Simple HSK is tested using the standard `@SpringBootTest` annotation with the `test` Spring profile in order to use an in-memory H2 database. [JaCoCo](https://www.jacoco.org/jacoco/) and [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/) are included as dependencies for test result outputs after running `mvn test`.

### Continuous Integration
The application is checked by CircleCI for continuous integration when changes are made to the `master` branch. CircleCI is able to run the app's integration tests thanks to the `application-test.properties` that sets up the in-memory H2 database.

---

### Technologies

- [Spring Boot](https://spring.io/projects/spring-boot)
- Spring Data REST
- Thymeleaf
- React
#### Spring Dependencies
- [Lombok](https://projectlombok.org/)
  - Provides Spring annotations for abstracting boilerplate Java class code.
- [JaCoCo](https://www.jacoco.org/jacoco/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)

#### React
- [Material UI](https://material-ui.com/)
  - Customized MUI components for HSK vocabulary lists and quizzes.
- [React Router](https://reactrouter.com/)
  - Used in the Auth page for navigating between the Login and Register forms.
