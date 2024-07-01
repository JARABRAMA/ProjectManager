package org.jarabrama.projectmanager.exception.task

class TaskNotFoundException(id: Long): TaskException("Task $id not found"){
}