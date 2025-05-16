# Task Service API Documentation

A comprehensive RESTful API for managing tasks. This API allows clients to create, read, update, and delete task records, with additional support for task completion and assignment tracking.

---

## Base URL

http://localhost:${SERVER_PORT}/api/v1

All endpoints are relative to the base URL.

---

## Endpoints

### Create Task

Creates a new task in the system.

- **URL**: `/create/`
- **Method**: `POST`
- **Request Headers**: `Content-Type: application/json`
- **Request Body**:
```json
{
  "title": "Complete Project Documentation",
  "description": "Write comprehensive documentation for the API project",
  "status": "In Progress",
  "priority": "High",
  "assignedTo": ["John Doe", "Jane Smith"]
}
````

* **Success Response**:

    * **Status Code**: `200 OK`
    * **Body**:

      ```json
      "Task created successfully"
      ```

* **Error Response**:

    * **Status Code**: `500 Internal Server Error`
    * **Body**:

      ```json
      "An error occurred while creating the task."
      ```

---

### Get All Tasks

Retrieves a list of all existing tasks.

* **URL**: `/tasks`
* **Method**: `GET`
* **Success Response**:

    * **Status Code**: `200 OK`
    * **Body**:

      ```json
      {
        "message": "Tasks retrieved successfully",
        "data": [
          {
            "id": 1,
            "title": "Complete Project Documentation",
            "description": "Write comprehensive documentation for the API project",
            "status": "In Progress",
            "priority": "High",
            "assignedTo": ["John Doe", "Jane Smith"]
          }
        ]
      }
      ```

---

### Get Task by ID

Fetches the details of a specific task by its unique ID.

* **URL**: `/tasks/{id}`
* **Method**: `GET`
* **URL Parameters**:

    * `id` (integer, required): The ID of the task to retrieve.
* **Success Response**:

    * **Status Code**: `200 OK`
    * **Body**:

      ```json
      {
        "message": "Task retrieved successfully",
        "data": {
          "id": 1,
          "title": "Complete Project Documentation",
          "description": "Write comprehensive documentation for the API project",
          "status": "In Progress",
          "priority": "High",
          "assignedTo": ["John Doe", "Jane Smith"]
        }
      }
      ```
* **Error Response**:

    * **Status Code**: `404 Not Found`
    * **Body**:

      ```json
      "Task not found."
      ```

---

### Update Task

Updates the details of an existing task.

* **URL**: `/update`
* **Method**: `PUT`
* **Request Headers**: `Content-Type: application/json`
* **Request Body**:

```json
{
  "id": 1,
  "title": "Updated Task Title",
  "description": "Updated task description",
  "status": "Completed",
  "priority": "Medium",
  "assignedTo": ["John Doe"]
}
```

* **Success Response**:

    * **Status Code**: `200 OK`
    * **Body**:

      ```json
      "Task updated successfully"
      ```

* **Error Response**:

    * **Status Code**: `500 Internal Server Error`
    * **Body**:

      ```json
      "An error occurred while updating the task."
      ```

---

### Delete Task

Deletes a task by its ID.

* **URL**: `/delete/{id}`

* **Method**: `DELETE`

* **URL Parameters**:

    * `id` (integer, required): The ID of the task to delete.

* **Success Response**:

    * **Status Code**: `200 OK`
    * **Body**:

      ```json
      "Task deleted successfully"
      ```

* **Error Response**:

    * **Status Code**: `500 Internal Server Error`
    * **Body**:

      ```json
      "An error occurred while deleting the task."
      ```

---

### Mark Task as Completed

Marks an existing task as completed.

* **URL**: `/complete/{id}`

* **Method**: `PATCH`

* **URL Parameters**:

    * `id` (integer, required): The ID of the task to mark as completed.

* **Success Response**:

    * **Status Code**: `200 OK`
    * **Body**:

      ```json
      "Task marked as completed successfully"
      ```

* **Error Response**:

    * **Status Code**: `500 Internal Server Error`
    * **Body**:

      ```json
      "An error occurred while marking the task as completed."
      ```

---

## Data Models

### Task Request Model

Describes the structure required to create or update a task.

```json
{
  "id": "integer (required for updates)",
  "title": "string (required)",
  "description": "string (optional)",
  "status": "string (e.g., 'New', 'In Progress', 'Completed')",
  "priority": "string (e.g., 'Low', 'Medium', 'High')",
  "assignedTo": ["string"] (optional)
}
```

### Task Response Model

Defines the structure of a task object returned by the API.

```json
{
  "id": "integer",
  "title": "string",
  "description": "string",
  "status": "string",
  "priority": "string",
  "assignedTo": ["string"]
}
```

### API Response Wrapper

All API responses follow a consistent structure:

```json
{
  "message": "string describing the result",
  "data": "object or array containing the response data"
}
```

---

## Error Handling

The API returns standard HTTP status codes to indicate success or failure of requests:

| Code | Description                          |
| ---- | ------------------------------------ |
| 200  | Request completed successfully       |
| 404  | The requested resource was not found |
| 500  | An internal server error occurred    |

In case of errors, the response will include a descriptive message in the response body.

---

## Rate Limiting

There are currently no rate limits applied to the API. Rate limiting may be introduced in future versions.

---

## Authentication

This API does not currently implement any authentication mechanism.

It is strongly recommended to implement authentication (e.g., API keys, JWT, OAuth) before deploying this API to a production environment.

```
```
