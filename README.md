## PROJECT DESCRIPTION
Title → 🐡 Grouper: A Group Management System

Figma Design: [link]  https://www.figma.com/file/bEgkv9AFmRQUQjScNOZ7Es/dbms-final-project---grouper?type=design&node-id=2077-165&mode=design&t=bjTKNLUDYziLItog-0 

Problem → With short semester classes and a busy workload, many university students struggle to find group members for their class projects that align with their skill sets. Forming project groups with a lack of initial socialization/introductions among peers can create unanticipated problems later on in the semester, ultimately leading to poor academic work.

Solution → 🐡 Grouper provides a simple solution to the dreaded group selection process. 
- Each student will make a profile on our website listing their specific technical strengths and weaknesses.
- The student profile will also include an average performance rating that is given by other students from past projects.
- Professors can ask students to join class pages where they can track the general progress of each project group.
- In each class page, students can browse through different student profiles and create productive project groups.

## HOW TO RUN
open docker and run mysql-server

on terminal 1: 
- connect to docker (docker exec -it mysql-server bash)
- in bash: (mysql -u root -p) password "mysqlpass"

on terminal 2: mvn spring-boot:run
-Dspring-boot.run.jvmArguments='-Dserver.port=8081'

Navigate to:
http://localhost:8080/login
or
http://localhost:8081/login 
**depending on how it works per person**

## to log in...
**create new accounts for professor and student to test with**

- professor: (first name) sami (last name) menik  (username) smenik (password) 123
- student: (first name) diya (last name) anand (username) danand (password) 123
- use credentials to login

## USING DATA.SQL 
**when attempting to login using accounts provided in data.sql file**

- Accounts in User table with ID # 1 - 749 have the password "123"
- Accounts in User table with ID # 750 - 1000 have the password "password"

## TECH USED
- Frontend: Spring Boot Framework, Mustache, CSS, Figma (UI design), Google Fonts (Josefin Sans and Jura)
- Backend: Java, Javascript
- Database: MySQL, Docker (database connection), Lucidchart (ER Diagram)
- Compiler: Maven


## DATABASE NAME + JDBC CONNECTION
database name: cs4370_final_project
