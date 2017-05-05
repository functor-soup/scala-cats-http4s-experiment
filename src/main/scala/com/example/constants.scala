package constants {
  object Constants {
    val mainUrl = "https://blockchain.info/ticker"

    def rateCall(curr:String,value:Float):String = s"https://blockchain.info/tobtc?currency=$curr&value=$value"
  }
}
