package au.com.machtech.ttf_stock_check.database
// (c) Copyright Skillage I.T.
// (c) This file is Skillage I.T. software and is made available under license.
// (c) This software is developed by: Joshua Panettieri
// (c) Date: 15th October 2022 Time: 08:00
// (c) File Name: TTF_Stock_Check Version: 1-0

data class UsersDb (
    val fullName: String? = "fullName",
    val email: String? = "email",
    val password: String? = "password")

