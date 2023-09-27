package com.rickbusarow.kase

class DirectoryBuilder(val name: String) {
  val children = mutableListOf<DirectoryBuilder>()
}
