package org.jarabrama.projectmanager.exception.developer

class DeveloperNotFoundException(id: Long): DeveloperException("developer $id not found")