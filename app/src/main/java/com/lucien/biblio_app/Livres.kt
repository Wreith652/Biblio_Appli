package com.lucien.biblio_app

data class Livres(
    var Image : String ?= null,
    var Nom : String ?= null,
    var Auteur : String ?= null,
    var Lu : Boolean ?= null,
    var Description : String ?= null,
    var Parution : String ?= null,
)
