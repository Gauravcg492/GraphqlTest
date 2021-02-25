Feature: User Login
	Scenario: User Logs in with right credentials
		Given User wants to login
		When User provides "<username>" and "<password>"
		Then User receives "<username>" and token
	Examples:
		| username | password |
		|123@gmail.com|apple123|