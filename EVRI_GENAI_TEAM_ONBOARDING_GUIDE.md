# 🎓 Evri GenAI Team Onboarding Guide

This guide provides ready-to-use GenAI prompts for onboarding team members to the Evri API Test Framework using AI assistants (ChatGPT, Claude, Gemini, etc.).

---

## 📖 Table of Contents

1. [Framework Learning Prompts](#framework-learning-prompts)
2. [How to Use These Prompts](#how-to-use-these-prompts)
3. [Progressive Learning Path](#progressive-learning-path)

---

## 🎯 Framework Learning Prompts

### 1️⃣ Complete Beginner (No Automation Experience)

```
I'm completely new to test automation and have been assigned to work with the Evri API Test Framework.

Framework Stack:
- Java 21
- REST Assured 5.5.6 (API testing library)
- Cucumber 6.11.0 (BDD framework)
- TestNG 7.11.0 (test runner)
- Maven (build tool)
- Triple reporting: TestNG HTML, ExtentReports, Allure

I need to understand:

**Week 1 - Absolute Basics:**
1. What is REST API and why do we test it?
2. What is BDD (Behavior Driven Development)?
3. How do I read a Cucumber feature file with Given/When/Then?
4. How do I run tests using Maven commands?
5. Where do I find test results and how do I read them?

**Week 2 - Framework Navigation:**
1. What does each folder in the project structure do?
2. What are POJOs and why do we use them?
3. How does a feature file connect to Java code?
4. What is the difference between Step Definitions and Service Layer?
5. How do I modify test data without breaking tests?

**Week 3 - Writing Simple Tests:**
1. Guide me to write my first GET API test scenario
2. How do I add assertions to verify response data?
3. How do I run only specific tests using tags?
4. What should I do when a test fails?
5. How do I read API request/response logs?

Please explain everything in simple terms, avoid jargon, and provide code examples.
Ask me questions to check my understanding before moving to the next topic.
```

---

### 2️⃣ Intermediate Tester (Has Basic Automation Knowledge)

```
I'm an automation tester with experience in Selenium/API testing. I'm learning the Evri API Test Framework which uses:

- Java 21, REST Assured 5.5.6, Cucumber 6.11.0, TestNG 7.11.0, Maven
- Layered architecture: Feature Files → Step Definitions → Service Layer → API Client
- Stateless service design with thread-safe property handling
- Triple reporting system (TestNG, ExtentReports, Allure)

Help me understand these intermediate concepts:

**1. Architecture & Design Patterns:**
- How does this layered architecture differ from Page Object Model?
- Why separate concerns into API Client, Service Layer, and Step Definitions?
- What is stateless service design and why use it here?
- How does the Builder pattern work in REST Assured request building?

**2. REST Assured Deep Dive:**
- How to build complex requests (headers, query params, body, auth)?
- How to handle different HTTP methods (GET, POST, PUT, DELETE, PATCH)?
- How to extract and validate specific JSON fields from responses?
- How to work with POJOs for serialization/deserialization?

**3. Cucumber Best Practices:**
- How to write reusable and maintainable step definitions?
- How to use Cucumber tags effectively (@smoke, @regression, etc.)?
- How to implement data-driven testing with Scenario Outlines?
- When should logic go in step definitions vs service layer?

**4. Framework Utilities:**
- How does PropertiesHandlers achieve thread-safety?
- How to use ExcelData for data-driven tests?
- How to customize logging and reporting?
- How to handle test configuration for different environments?

**5. Practical Exercises:**
- Walk me through adding a new API endpoint to test
- Show me how to create a complete test (feature → steps → service → POJOs)
- Help me debug common failures (null responses, assertion errors, etc.)
- Guide me to generate and analyze Allure reports

Provide code examples from the Evri API Test Framework style and let me ask follow-up questions.
```

---

### 3️⃣ Advanced Tester (Framework Maintenance & Extension)

```
I'm responsible for maintaining and extending the Evri API Test Framework.

Current Architecture Score: 9.0/10
Tech Stack: Java 21, REST Assured 5.5.6, Cucumber 6.11.0, TestNG 7.11.0, Maven

Framework Key Features:
✅ Thread-safe property loading (singleton pattern)
✅ Stateless service layer
✅ Fail-fast validation strategies
✅ Layered architecture with clean separation
✅ Triple reporting (TestNG, ExtentReports, Allure)

I need advanced guidance on:

**1. Framework Architecture:**
- Why was stateless service design chosen over instance-based services?
- How does thread-safe property loading improve performance?
- What are the trade-offs of this layered architecture?
- How to maintain SOLID principles when adding new features?

**2. Performance Optimization:**
- What is fail-fast validation and when should it be used?
- How to identify and fix performance bottlenecks?
- Best practices for parallel test execution?
- How to optimize large JSON payload processing?

**3. Framework Extension:**
- How to add support for new APIs (authentication, endpoints)?
- How to implement custom validators and matchers?
- How to extend reporting with custom screenshots/attachments?
- How to integrate with CI/CD pipelines (Jenkins, GitHub Actions)?

**4. Code Quality & Standards:**
- What coding standards should be enforced in code reviews?
- How to ensure thread-safety when adding new utilities?
- When to use static methods vs instance methods?
- How to handle exception scenarios gracefully?

**5. Troubleshooting & Debugging:**
- How to debug REST Assured request building issues?
- How to trace failures in BDD scenarios (which layer failed)?
- How to handle flaky tests in API automation?
- Common pitfalls and how to avoid them?

**6. Migration & Refactoring:**
- How to refactor legacy tests to this framework pattern?
- How to migrate from one reporting tool to another?
- How to upgrade dependencies safely (REST Assured, Cucumber)?
- How to maintain backward compatibility during changes?

Provide architectural insights, code examples, and review checklists.
```

---

### 4️⃣ API-Specific Learning (Hands-On Practice)

```
I want to practice adding a new API to the Evri API Test Framework.

Let's say I need to test the "JSONPlaceholder API" (https://jsonplaceholder.typicode.com):
- GET /posts (get all posts)
- GET /posts/{id} (get specific post)
- POST /posts (create post)
- PUT /posts/{id} (update post)
- DELETE /posts/{id} (delete post)

Guide me step-by-step:

**Step 1: Configuration**
- How do I add this API's base URI to config.properties?
- What configuration values do I need (authentication, headers, etc.)?

**Step 2: POJOs (Serialization/Deserialization)**
- How do I create POJOs for Post request/response?
- Show me the structure with proper annotations
- How do I handle nested JSON objects?

**Step 3: Service Layer**
- Create a PostService.java with all CRUD methods
- How to implement validation methods (status codes, response fields)?
- How to make it stateless like ParcelShopService?

**Step 4: Feature File**
- Write a complete Posts.feature file with scenarios for all operations
- Use proper Gherkin syntax with Given/When/Then
- Add appropriate tags (@smoke, @crud, @posts)

**Step 5: Step Definitions**
- Create PostSteps.java with step definitions
- How to handle parameters from feature files?
- How to store and reuse data between steps (e.g., created post ID)?

**Step 6: Test Execution**
- Update testng.xml to include the new runner
- Run tests and verify all scenarios pass
- Generate all three reports (TestNG, ExtentReports, Allure)

Provide complete code examples for each step following Evri API Test Framework patterns.
```

---

## 📚 How to Use These Prompts

1. **Choose Your Level**: Select beginner, intermediate, advanced, or hands-on practice prompt
2. **Copy the Prompt**: Copy the entire prompt text including context
3. **Paste to AI**: Use ChatGPT, Claude, Gemini, or any AI assistant
4. **Interactive Learning**: Ask follow-up questions, request clarifications
5. **Practice**: Apply what you learn in the actual framework
6. **Progress**: Move to next level once comfortable

### Tips for Best Results:

✅ **Be Specific**: Mention exact file names and line numbers  
✅ **Provide Code**: Share actual code snippets for review  
✅ **Ask Follow-ups**: Don't hesitate to ask "why" and "how"  
✅ **Iterative Learning**: Break down complex topics into smaller parts  
✅ **Practice Immediately**: Try concepts right after learning  

---

## 🔗 Building Your Own Framework?

If you want to create a new framework from scratch (not learn this one), refer to:
- **[EVRI_GENAI_FRAMEWORK_DEVELOPMENT_SHOWCASE.md](EVRI_GENAI_FRAMEWORK_DEVELOPMENT_SHOWCASE.md)** - Complete methodology for building frameworks using GenAI prompt engineering, including the 4-phase approach with architectural definition, best practices integration, documentation, and maintenance strategies.

---

## 🎯 Progressive Learning Path

### Month 1: Foundation
- **Week 1**: Understand project structure, run existing tests
- **Week 2**: Read and modify feature files, understand BDD
- **Week 3**: Explore step definitions and service layer
- **Week 4**: Generate and analyze all three reports

### Month 2: Intermediate Skills
- **Week 1**: Write new scenarios for existing APIs
- **Week 2**: Create POJOs and understand serialization
- **Week 3**: Work with PropertiesHandlers and configuration
- **Week 4**: Debug failed tests and fix issues

### Month 3: Advanced Mastery
- **Week 1**: Add a complete new API to framework
- **Week 2**: Optimize performance and implement fail-fast
- **Week 3**: Customize reporting and logging
- **Week 4**: Conduct code reviews and mentor others

---

## 🔄 Continuous Improvement

**For Learners:**
- Use these prompts every time you're stuck
- Keep a learning journal of concepts mastered
- Contribute to framework documentation as you learn
- Practice hands-on with the API-specific learning prompts

**For Team Leads:**
- Use learning prompts to onboard new team members
- Customize prompts based on team skill levels
- Track team progress through learning paths
- Maintain a knowledge base of common questions and solutions

---

## 📞 Support & Resources

- **Framework README**: `/README.md`
- **GenAI Development Showcase**: `/EVRI_GENAI_FRAMEWORK_DEVELOPMENT_SHOWCASE.md`
- **This Guide**: `/EVRI_GENAI_TEAM_ONBOARDING_GUIDE.md`

**External Resources:**
- [REST Assured Documentation](https://rest-assured.io/)
- [Cucumber Best Practices](https://cucumber.io/docs/guides/10-minute-tutorial/)
- [TestNG Documentation](https://testng.org/doc/)
- [Allure Framework](https://docs.qameta.io/allure/)

---

**Last Updated**: November 16, 2025  
**Framework Version**: 0.0.1-SNAPSHOT  
**Maintained By**: QA Automation Team

---

## 💡 Pro Tips

1. **Start Small**: Don't try to learn everything at once
2. **Hands-On**: Type the code yourself, don't just copy-paste
3. **Ask Why**: Understand the reasoning behind design decisions
4. **Review Often**: Use code review prompts monthly
5. **Share Knowledge**: Teach others what you've learned
6. **Stay Updated**: Keep prompts aligned with framework evolution

---

*These GenAI prompts are designed to work with any AI assistant. Adjust context and details based on your specific learning needs or review requirements.*
