package com.scalacode

/**
 * Created by wangxy on 15-7-30.
 */
object tcase {

  abstract class Item
  case class Article(description: String, price: Double) extends Item
  case class Bundle(description: String, price: Double, items: Item*) extends Item


  val kankan = Bundle("Father's day special", 20.0,
    Article("Scala for the Impatient", 39.95),
    Bundle("Anchor Distillery Sampler", 10.0,
      Article("Old Potrero Straight Rye Whisky", 79.95),
      Article("Junipero Gin", 32.95)
    )
  )

  kankan match{
    case Bundle(_, disc, its @ _*) => println(disc)
  }

  kankan match{
    case Bundle(_, disc, its @ _*) => println(its)
  }

  def price(it: Item): Double = it match {
    case Article(_, p) => p
    case Bundle(_, disc, its @ _*) => its.map(price _).sum - disc
  }
}
