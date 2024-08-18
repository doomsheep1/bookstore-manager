# bookstore-manager
Java Spring Boot REST API take home assignment requirements:

**Following API operations:**

•	Add a new book. 

•	Update book

•	Find books by title and/or author (Exact Match) 

•	Delete book (Restricted permission, admin only)

**Notes:**

•	A book is identified uniquely by its ISBN and can have more than 1 author.

•	All books info (Book information like title, ISBN, Year of Publication, Author(s), Price, Genre etc.) must be stored in a database.

•	No UI is required, but you are expected to demonstrate the output from each operation. Handling of errors is expected as well.

•	Design your API as a protected resource

•	The delete book API should be restricted to only authorized role/user


**Required dependencies:**
1. Requires JDK 17 minimum
2. CURL command line tool to make http requests (CURL is included as a command line tool by default in windows 10 command prompt), alternatively you may use PostMan
3. Notepad or Notepad++ (preferred) to prepare your API request formats

**The project includes the following:**
1. REST API endpoints that perform CRUD (create, read, update, delete) operations on books
2. For security, OAuth2 authorization and resource server (single server) to get access tokens which will enable access for the REST API endpoints
3. Embedded H2 database, data only persists for as long as application is running. On application restart, all database data will be reset.


**The following section are command line operations:**

normal-client bearer tokens request samples
-------------------------------------------
Create book access token:

curl -v -X POST normal-client:secret-client@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:create"

Read book access token:

curl -v -X POST normal-client:secret-client@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:read"

Update book access token:

curl -v -X POST normal-client:secret-client@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:update"


admin-client bearer tokens request samples
------------------------------------------
Create book access token:

curl -v -X POST admin-client:secret-admin@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:create"

Read book access token:

curl -v -X POST admin-client:secret-admin@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:read"

Update book access token:

curl -v -X POST admin-client:secret-admin@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:update"

Delete book access token:

curl -v -X POST admin-client:secret-admin@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=book:delete"

result samples:

{"access_token":"eyJraWQiOiIxMDA5NDk4NS1jMjZhLTQyM2QtOTM4My0xYjJkOGU2NWYyN2UiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhZG1pbi1jbGllbnQiLCJhdWQiOiJhZG1pbi1jbGllbnQiLCJuYmYiOjE3MjM5NTAwMzYsInNjb3BlIjpbImJvb2s6ZGVsZXRlIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsImV4cCI6MTcyMzk1MDMzNiwiaWF0IjoxNzIzOTUwMDM2LCJqdGkiOiJhM2UyNzhlNS0wMjJiLTQ5YjMtYTc3Mi01NTdlMTdjZDRlOGMifQ.vLwiO9z0JHa2qN2HNhDQqMJ0_y_Pkwbrl2a9STfQBAovWCn2_mLtc3GhQ_NfgzVTWP8AHVQUyh9Dfoj8fwW2dcCBpKIAN1tTD5JR6abjXtg3JU3oer7nolp6jdIpFLpXa3X6nTsqPXyTQAh3I8w4X8ZltVLi_Pg-LKZnWi7AHOormGmaB_rUn9RVZCUoojqcwIpBYLuQtuG2Hd-F21mZ4dt_xL6o_LJlLs9AjiIKYlAxCbQT2yDFEJ_SzS2FEzge4YyyZNyEwlBMIfwqFtY8RwlfoRZ4oMJ09ybbprwyE9UB-x_MMld_pUyQ-aAqwTAEAs1cdY9ubac-4FQlP1AGUA","scope":"book:delete","token_type":"Bearer","expires_in":300}

accessing endpoints
-------------------
find book by title (exact match):

curl -X POST -H "Authorization: Bearer your-read-access-token" localhost:8080/find-book --data-urlencode "title=<your-title>" -v

find book by authors (exact match):

curl -X POST -H "Authorization: Bearer your-read-access-token" localhost:8080/find-book --data-urlencode "authors=<your-author1>,<yourauthor2>" -v

find book by title AND authors (exact match):

curl -X POST -H "Authorization: Bearer your-read-access-token" localhost:8080/find-book --data-urlencode "title=<your-title>" --data-urlencode "authors=<your-author1>,<yourauthor2>" -v

add book:

curl -X POST -H "Authorization: Bearer your-create-access-token" -H "Content-Type: application/json" -d "<your-create-book-json-request>" localhost:8080/add-book -v

update book (by isbn only):

curl -X PUT -H "Authorization: Bearer your-update-access-token" -H "Content-Type: application/json" -d "<your-update-book-json-request" localhost:8080/update-book/<your-isbn-to-be-updated> -v

delete book (by isbn only):

curl -X DELETE -H "Authorization: Bearer your-delete-access-token" localhost:8080/delete-book --data-urlencode "isbn=<your-isbn-to-be-deleted>" -v

request samples:

curl -X POST -H "Authorization: Bearer eyJraWQiOiJmZTBiZDlhMS03OTRiLTQxMjUtYTExZi0yM2Y2OTk1YWY4MDgiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJub3JtYWwtY2xpZW50IiwiYXVkIjoibm9ybWFsLWNsaWVudCIsIm5iZiI6MTcyMzk0OTQ1Miwic2NvcGUiOlsiYm9vazpjcmVhdGUiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzIzOTQ5NzUyLCJpYXQiOjE3MjM5NDk0NTIsImp0aSI6IjhmNDM2ZjdmLWNhODQtNDBhMi05MGFjLTMwZDNlYTAyNGEwZiJ9.ewu5lS0Rl2ayZVgAILryO7FAeNCyhOyg5l9v4aU-NiKF3KWOJ3PDXvQ9OoqYsjTXUvf06VPpBxX8oLVOHNwUlDL0DtY6TNHfpO2_Yi98AJYQPoaHAy949tTRTb1of1e2cmuX3GTf26PNNj3n3LKzerJ9UijRt-OfQq1Um3BE_eV_rEtG1qv5qEPUrcziDtDMMlYJujcYb5u9EFuUeOqVcK7zE9CqrYHRJmTWqtLl7B_BK_siyW5-wlXa2RZSzQ-3-7KIjQ9Jkv1FMQCctY2RhN85v_dQ2WEzbQK4t9BPSBzkuOezw-IR0NtVg9xlWlaCS2niHeT8w8LACWl-olUVmQ" -H "Content-Type: application/json" -d "{\\"title\\": \\"The Great Gatsby\\", \\"publishedYear\\": 1925, \\"price\\": 10.99, \\"genre\\": \\"Fiction\\", \\"authors\\": [{\\"name\\": \\"F. Scott Fitzgerald\\", \\"birthDay\\": \\"24-09-1896\\"}]}" localhost:8080/add-book -v

result samples:

{"status":"200 OK","message":"Books found","bookEntityList":[{"isbn":"037235bc-bfd8-4820-830f-b830a562e8d5","title":"The Shining","authors":[{"name":"Stephen King","birthDay":"21-09-1947"},{"name":"F. Scott Fitzgerald","birthDay":"24-09-1896"}],"publishedYear":1925,"price":10.99,"genre":"Fiction"}]}

Usable sample Books that were added on application startup
----------------------------------------------------------
Book 1:
Title - Harry Potter and the half blood prince
Author - JK Rowling, JK Rowling 2

Book 2:
Title - Twilight
Author - Stephanie Meyer, Stephanie Meyer jr

Book 3:
Title - The Shining
Author - Stephen King

**How to start the application (Work - in - progress)**
------------------------------------------------------


