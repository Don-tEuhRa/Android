package com.dongminpark.reborn.Frames

import androidx.compose.runtime.Composable

@Composable
fun FinalPayPriceFrame(price: Int, deliveryPrice: Int = 4000, point: Int) {
    // 결제 금액
    TextFormat(text = "결제 금액")
    // 상품 금액
    RowSpaceBetweenFrame(first = "상품 금액", second = price.toString() + "원")
    // 배송비
    RowSpaceBetweenFrame(first = "배송비", second = deliveryPrice.toString() + "원")
    // 사용 포인트
    RowSpaceBetweenFrame(first = "사용 포인트", second = point.toString() + "원")
    // 총 결제 금액
    RowSpaceBetweenFrame(first = "총 결제 금액", second = (price + deliveryPrice - point).toString() + "원")
}