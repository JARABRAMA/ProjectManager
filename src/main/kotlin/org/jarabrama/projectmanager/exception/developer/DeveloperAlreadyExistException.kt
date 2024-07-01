package org.jarabrama.projectmanager.exception.developer

class DeveloperAlreadyExistException(email: String): DeveloperException("Developer $email already exist")  {
}