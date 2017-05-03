package Logger {

  object Printer {

    def msgType(msgType: String): (String => String) = {
      (msg: String) => s"[$msgType] $msg \n"
    }

    val debug = msgType("debug")
    val info = msgType("info")
    val warning = msgType("warning")
    val verbose = msgType("verbose")
  }

}
