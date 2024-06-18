# Description
Abstraindo um Bootcamp Usando Orientação a Objetos em Java.

Just planning. No implementation.

# Entity Relationship Diagram
```mermaid
erDiagram
    resources }o--o{ resource_tags : ""
    resource_tags }o--o{ content_tags : ""
    resources }o--o{ course_resources : ""
    content_tags }o--o{ mentorship_tags : ""
    bootcamps }o--o{ bootcamp_mentorships : ""
    bootcamp_mentorships }o--o{ mentorships : ""
    bootcamps }o--o{ bootcamp_courses : ""
    bootcamp_courses }o--o{ courses : ""
    mentorships ||--|| users : ""
    courses ||--|| users : ""
    bootcamps ||--|| users : ""
    users }o--o{ user_roles : ""
    user_roles }o--o{ roles : ""
    bootcamps }o--o{ bootcamp_subscribers : ""
    mentorships }o--o{ mentorship_subscribers : ""
    courses }o--o{ course_subscribers : ""
    bootcamp_subscribers }o--o{ users : ""
    mentorship_subscribers }o--o{ users : ""
    course_subscribers }o--o{ users : ""
    course_resources }o--o{ courses : ""
    mentorship_tags }o--o{ mentorships : ""

    courses {
        Integer  id
        String   title
        String   description
        Datetime startDatetime
        Datetime endDatetime
        Integer  experiencePoints
        Integer  coordinatorId
    }
    mentorships {
        Integer  id
        String   title
        String   description
        Datetime sessionStart
        Integer  expectedDuration
        Integer  experiencePoints
        Integer  coordinatorId
    }
    bootcamps {
        Integer  id
        String   title
        String   description
        Datetime startDatetime
        Datetime endDatetime
        Integer  coordinatorId
    }
    bootcamp_courses {
        Integer bootcampId
        Integer courseId
    }
    bootcamp_mentorships {
        Integer bootcampId
        Integer mentorshipId
    }
    users {
        Integer id
        String  firstName
        String  lastName
        String  emailAddress
        String  password
    }
    roles {
        Integer id
        String  name
    }
    user_roles {
        Integer userId
        Integer roleId
    }
    bootcamp_subscribers {
        Integer   bootcampId
        Integer   userId
        Datetime  registrationDatetime
        Datetime  completionDatetime
    }
    course_subscribers {
        Integer courseId
        Integer userId
        Datetime  registrationDatetime
        Datetime  completionDatetime
    }
    mentorship_subscribers {
        Integer  mentorshipId
        Integer  userId
        Datetime registrationDatetime
        Datetime completionDatetime
    }
    resources {
        Integer id
        Blob    checksum
        String  mimeType
        String  accessEndpoint
        Integer sizeMegabytes
    }
    course_resources {
        Integer courseId
        Integer resourceId
    }
    resource_tags {
        Integer resourceId
        Integer tagId
    }
    content_tags {
        Integer id
        String  name
        Integer parentTagId
    }
    mentorship_tags {
        Integer mentorshipId
        Integer tagId
    }
```

**Notes:**
- For simplicity, courses, mentorships, and bootcamps will each have only one coordinator (If not, three more many-to-many tables would be needed);
- `resources` table has references to a content delivery network;
- `content_tags` entries build a tree of tags used to categorize the contents covered in mentorships, courses, and bootcamps. Bootcamp tags will be inherited from the courses and mentorships it wraps;
- `user_roles` and `roles` are the base of the application access control. I think `Student`, `Instructor`, `Editor`, and `Admin` are good roles for the domain. `Editor` and `Instructor` will have to work together to build new contents: `Editor` and `Instructor` get together to plan the syllabus, `Editor` builds the new content and upload it into the platform, and after creation the `Instructor` have access to a series of infomations and metrics to accompany `Student`s subscriptions, engagement, and progress;
- I have no idea how permissions are implemented in practice. This planning only states that RBAC is used.

# Class Diagram
```mermaid
classDiagram
    Bootcamp --|> Activity
    Course --|> Activity
    Mentorship --|> Activity
    User *-- UserRole
    class Bootcamp {
        -Datetime startEnrollmentPeriod
        -Datetime endEnrollmentPeriod
        -Datetime startBootcampPeriod
        -Datetime endBootcampPeriod
        -Mentorship[] mentorships
        -Course[] courses
    }
    class UserRole {
        -Integer id
        -String  name
    }
    class User {
        -Integer id
        -String firstName
        -String lastName
        -String emailAddress
        -UserRole role
    }
    class Activity {
        -Integer id
        -String title
        -String description
        -Integer experiencePoints
        -User coordinator
        -User[] subscribers

        +buildPage(User user, HTMLPage template) HTMLPage*
        +subscribeUser(User toSubscribe) void*
    }
    class Mentorship {
        -Datetime sessionStart
        -Integer durationMinutes
    }
    class Course {
        -Integer totalHours

        +getProgress(User subscriber) Double
    }
```

**Notes:**
- Getters and setters are left out;
- Authentication helper classes (like in [lab-oop-2](../lab-oop-2)) are left out;
- Authorization helper classes (If any is necessary) are left out (I don't know yet how RBAC is implemented);
- *Italic* methods are abstract.

# Resources
| Description | Recommendation (1-5) |
| :---        | :---                 |
| [Introduction to the Java 8 Date/Time API](https://www.baeldung.com/java-8-date-time-intro) | 4 |
| [User Role and Permission Management: Designing Efficient Models](https://frontegg.com/guides/user-role-and-permission) | 3 |
| [Spring Boot Security - Users & Roles](https://youtu.be/L8M_eXV0OVk?si=_YRR0bfFZYrk9z9i) | 5 |
| [Designing Role-Based Access Control](https://medium.com/@kamalmeet/designing-role-based-access-control-63c17a32894) | 5 |
| [Implementing Role Based Security in a Web App](https://medium.com/bluecore-engineering/implementing-role-based-security-in-a-web-app-89b66d1410e4) | 5 |
| [Overview of access control](https://cloud.google.com/storage/docs/access-control) |  |
