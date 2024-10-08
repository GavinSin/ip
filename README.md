# Mira project template

This is a project template for a greenfield Java project. It's named after the Java mascot _Mira_. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 17, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 17** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
3. After that, locate the `src/main/java/Mira.java` file, right-click it, and choose `Run Mira.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:

## Acknowledgements

1. I have written the required code myself, and use ChatGPT to generate alternative implementations, use to improve my own coding skills.
2. I have referenced AB3 repository for code design inspiration.
3. I have referenced the JavaFx tutorial guide for GUI implementation.