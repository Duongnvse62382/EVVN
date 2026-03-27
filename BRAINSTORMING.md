# BRAINSTORMING & DESIGN WORKFLOW

## Purpose & Description
This document defines the standard workflow for collaborative brainstorming, design specification, and implementation planning within the EVVN project. It guides interactions between the User and the Gemini CLI agent to ensure a structured and efficient development process, mirroring the procedural nature of a specialized skill.

## AI Agent Persona & Role
**Persona:** Senior Software Engineer / AI Collaborator
**Role:** To facilitate structured discussions, act as a subject matter expert, translate requirements into specifications, and generate actionable implementation plans.

## User Role
**Role:** Project Stakeholder / Requester
**Role:** To provide initial requirements, engage in clarifying discussions, review specifications and plans, and give final approval.

## Workflow Stages

### Stage 1: Expert Discussion & Requirement Clarification

*   **Objective:** Elicit, clarify, and define requirements, scope, technical approaches, and potential challenges.
*   **Trigger:** User initiates a new feature or task by describing their need.
*   **Process:**
    *   Gemini CLI acts as an expert, asking targeted questions and exploring design alternatives.
    *   Discussion covers architecture, user experience, data models, and error handling.
    *   Key decisions are made collaboratively.
*   **Gemini CLI Tools/Skills Involved:** `ask_user` (for clarifications), `google_web_search` (for research), potentially `brainstorming` skill (if applicable).

### Stage 2: Specification (`spec.md`) Generation

*   **Objective:** Document the agreed-upon requirements and design decisions in a formal specification.
*   **Trigger:** Completion of Stage 1 discussion.
*   **Process:**
    *   Gemini CLI will draft a `spec.md` file with a naming convention `DDMMYYYY-ev-Title.md` (e.g., `27032026-ev-FeatureName.md`) and save it within the `docs/supperpower/specs/` directory, based on the discussion.
    *   Content includes: Summary, Requirements, Technical Approach, UI/UX Notes, Data Models, Error Handling, Scope.
*   **Gemini CLI Tools/Skills Involved:** `write_file` (to create/update `spec.md` in `docs/supperpower/specs/` directory).
*   **User Action:** Review and approve the `spec.md` located in the `docs/supperpower/specs/` directory.

### Stage 3: Implementation Planning (`plan.md`)

*   **Objective:** Translate the approved `spec.md` into an actionable, step-by-step implementation plan, saved as `plan.md` with a naming convention `DDMMYYYY-ev-Title.md` (e.g., `27032026-ev-FeaturePlan.md`) in the `docs/supperpower/plans/` directory.
*   **Trigger:** Approval of the `spec.md` file.
*   **Process:**
    *   Gemini CLI leverages the `spec.md` (read from `docs/supperpower/specs/`) to outline implementation steps, file modifications, testing strategy, and dependencies. The generated `plan.md` will be saved in the `docs/supperpower/plans/` directory using the specified naming convention.
    *   This stage may invoke specialized planning tools or skills, such as the `writing-plans` skill.
*   **Gemini CLI Tools/Skills Involved:** `writing-plans` skill, `executing-plans` skill, `read_file` (to reference `spec.md` in `docs/supperpower/specs/`), `write_file` (to create/update `plan.md` in `docs/supperpower/plans/` directory).
*   **User Action:** Review the `plan.md` located in the `docs/supperpower/plans/` directory and provide approval to proceed with execution.

## Usage Instructions

1.  To initiate a new design or feature planning session, clearly state your intent (e.g., "Let's brainstorm a new feature for user profiles").
2.  Engage actively in the discussion phases.
3.  Be aware that generated files will follow the naming convention `DDMMYYYY-ev-Title.md` and be saved in `docs/supperpower/specs/` (for specs) and `docs/supperpower/plans/` (for plans). Review all generated documents carefully before approval.

## Key Principles for this Workflow

*   **Clarity:** Ensure all requirements and plans are unambiguous.
*   **Collaboration:** Active participation from both User and Gemini CLI is crucial.
*   **Documentation:** Maintain clear and up-to-date specifications and plans in designated directories with consistent naming.
*   **Tool Leverage:** Utilize appropriate Gemini CLI tools and skills for efficiency.
*   **Iterative Refinement:** Be open to feedback and iterative improvements throughout the process.
