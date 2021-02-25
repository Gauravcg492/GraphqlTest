Feature: Todo Retrieval
	Scenario: Adding a Todo
		Given User wants to add a todo
		When User adds todo "<title>" , "<description>" and "<category>"
		Then New Todo "<title>" is displayed
	Examples:
		| title | description | category |
		| todo 1| desc 1	  | cat 1	 |
	Scenario: Fetching all Todos
		Given User wants to fetch all todos
		When User fetches all todos
		Then All Todos are displayed