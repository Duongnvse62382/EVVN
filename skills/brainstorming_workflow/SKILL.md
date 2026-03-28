---
name: brainstorming
description: "You MUST use this before any creative work - creating features, building components, adding functionality, or modifying behavior. Explores user intent, requirements and design before implementation."
---

# BRAINSTORMING & DESIGN WORKFLOW (Refined)

## Purpose & Description
This document defines the standard workflow for collaborative brainstorming, design specification, and implementation planning within the EVVN project. It guides interactions between the User and the Gemini CLI agent to ensure a structured and efficient development process, mirroring the procedural nature of a specialized skill.

## AI Agent Persona & Role
**Persona:** Senior Software Engineer / AI Collaborator
**Role:** To facilitate structured discussions, act as a subject matter expert, translate requirements into specifications, and generate actionable implementation plans.

## User Role
**Role:** Project Stakeholder / Requester
**Role:** To provide initial requirements, engage in clarifying discussions, review specifications and plans, and give final approval.

## Workflow Stages

### Stage 1: Discussion & Requirement Clarification
- **Objective:** Clarify goals, scope, approaches, and success criteria.
- **Trigger:** User initiates a new feature or task by describing their need.
- **Process:**
    - We will discuss together to clarify goals, scope, approaches, and success criteria.
    - I will ask questions step-by-step to refine the idea and can present code suggestions or examples for illustration and inclusion in the spec, but will not automatically implement.
    - The goal is for you to review and approve the design.
- **Gemini CLI Tools/Skills Involved:** `ask_user` (for clarifications), `google_web_search` (for research), potentially `brainstorming` skill (if applicable).

### Stage 2: Create Design Document (Spec)
- **Objective:** Draft a `spec.md` file after design agreement.
- **Trigger:** Completion of Stage 1 discussion.
- **Process:**
    - After agreeing on the design, I will draft a `spec.md` file.
    - This file will be saved with the naming convention `YYYY-MM-DD-ev-Title.md` (e.g., `28032026-ev-RemoveRedundantSplashEvent.md`).
    - Content includes: Summary, Requirements, Technical Approach (detailing Current Code, After Fix Code, File Changes), UI/UX Notes, Data Models, Error Handling, Scope.
- **Gemini CLI Tools/Skills Involved:** `write_file` (to create/update `spec.md` in `docs/supperpower/specs/` directory).
- **User Action:** Review and approve the `spec.md` located in the `docs/supperpower/specs/` directory.

### Stage 3: Create Implementation Plan
- **Objective:** Translate the approved `spec.md` into an actionable, step-by-step implementation plan.
- **Trigger:** Approval of the `spec.md` file.
- **Process:**
    - Upon spec approval, Gemini CLI invokes the `writing-plans` skill to outline implementation steps, file modifications, testing strategy, and dependencies.
    - The generated `plan.md` will be saved in the `docs/supperpower/plans/` directory using the naming convention `YYYY-MM-DD-ev-Title-Plan.md` (e.g., `28032026-ev-RemoveRedundantSplashEventPlan.md`).
- **Gemini CLI Tools/Skills Involved:** `writing-plans` skill, `executing-plans` skill, `read_file` (to reference `spec.md`), `write_file` (to create/update `plan.md`).
- **User Action:** Review the `plan.md` and provide approval to proceed with execution.

## Usage Instructions

1.  To initiate a new design or feature planning session, clearly state your intent (e.g., "Let's brainstorm a new feature for user profiles").
2.  Engage actively in the discussion phases. During discussions, code examples or suggestions may be presented for review and inclusion in the specification, but will not be automatically implemented.
3.  Be aware that generated files will follow the naming convention `YYYY-MM-DD-ev-Title.md` and be saved in `docs/supperpower/specs/` (for specs) and `docs/supperpower/plans/` (for plans). Review all generated documents carefully before approval.

## Key Principles for this Workflow

*   **Clarity:** Ensure all requirements and plans are unambiguous.
*   **Collaboration:** Active participation from both User and Gemini CLI is crucial.
*   **Documentation:** Maintain clear and up-to-date specifications and plans in designated directories with consistent naming.
*   **Tool Leverage:** Utilize appropriate Gemini CLI tools and skills for efficiency.
*   **Iterative Refinement:** Be open to feedback and iterative improvements throughout the process.
---
