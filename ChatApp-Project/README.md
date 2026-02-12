# Take Home Assignment / Mobile (All Platforms)

## :warning: Please read these instructions carefully and entirely first
* Clone this repository to your local machine.
* Use your IDE of choice to complete the assignment.
* When you have completed the assignment, you need to  push your code to this repository and [mark the assignment as completed by clicking here](https://app.snapcode.review/submission_links/0b42d855-08eb-4c0c-b9fb-4a9a4a80c37a).
* Once you mark it as completed, your access to this repository will be revoked. Please make sure that you have completed the assignment and pushed all code from your local machine to this repository before you click the link.

## Assignment Details

* We will be developing a simple chat interface, similar to what you will see in many familiar mobile apps.

* The output should contain all relevant code, tests, explanatory notes, plus any build scripts you would like to include.

* Please do not include binaries or external packages or libraries you have used.

We respect your time and understand that asking you to give up an hour or so of your personal time for the interview process is a big ask.

However, we want to make the process as simple and stress-free as possible, by allowing you to complete the first stage of the process in the comfort of your own home.

We will also be using your submission in further stages of the process should you be successful.

## What we are looking for

### Test Coverage

The solution should be developed “test-first”, should have good unit tests, and core functionality should be covered. Your tests should also be self-contained and not rely on external systems to be available to run.

### Simplicity

We value simplicity as an architectural virtue and a development practice. Solutions should reflect the difficulty of the assigned task, and should not be overly complex. Layers of abstraction, patterns, or architectural features that aren’t called for should not be included.

### Self-explanatory code

The solution you produce must speak for itself. Multiple paragraphs explaining the solution are a sign that it isn’t straightforward enough to understand purely by reading code, and are not appropriate.

## The Problem Statement

Create a simple demo chat app that has some basic capabilities:

1. Type in a text message and press a send button. The message should appear in a list on the right side of the view.

1. Simulate a reply which appears magically after a 5 second delay. The message should appear on the left beneath the previous message.

## FAQ

- **What other functionality is required?**  
  No other capabilities are required. This is not a trick question and there is no single correct answer. We prefer simple, well tested solutions over clever solutions. The complexity of your solution should reflect that of the problem.

- **What about ambiguity?**  
  If there is any ambiguity please add this in a section added to the bottom of the README and make a choice yourself to resolve the ambiguity.

- **How should persistence be handled?**  
  In-memory persistence is sufficient for the exercise.

- **Is there a time limit for completing the exercise?**
  There is no time limit for how long the exercise should take for you to complete, but we find most people complete the exercise in around 90 mins.

## Candidates Notes

## Project Structure
```
com.assignment.chatapp
│
├── data
│   ├── datasource
│   ├── mapper
│   └── repository
│
├── domain
│   ├── model
│   ├── repository
│   └── usecase
│
├── ui
│   ├── composables
│   ├── theme
│   ├── uistates
│   └── viewmodel
│
├── di
│
├── MainActivity
└── ChatApp (Application class)

```

