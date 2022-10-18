package com.example.numbertesttask2.numbers.domain

abstract class DomainException: IllegalStateException()

class NoInternetConnectionException : DomainException()

class ServiceUnavailableException: DomainException()