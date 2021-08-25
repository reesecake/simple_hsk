# Simple HSK
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
## Testing
Simple HSK is tested using the standard `@SpringBootTest` annotation with the `test` Spring profile in order to use an in-memory H2 database. [JaCoCo](https://www.jacoco.org/jacoco/) and [Maven Surefire](https://maven.apache.org/surefire/maven-surefire-plugin/) are included as dependencies for test result outputs after running `mvn test`.

### CI
The application is checked by CircleCI for continuous integration when changes are made to the `master` branch. CircleCI is able to run the app's integration tests thanks to the `application-test.properties` that sets up the in-memory H2 database.

---

### Technologies

- Spring Boot
- Spring Data REST
- Thymeleaf
- React
#### Spring Dependencies
- [JaCoCo](https://www.jacoco.org/jacoco/)
- [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/)
