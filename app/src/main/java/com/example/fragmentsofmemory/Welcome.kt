package com.example.fragmentsofmemory

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.afollestad.date.dayOfMonth
import com.afollestad.date.month
import com.afollestad.date.year
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.datePicker
import com.example.fragmentsofmemory.fragments.AddingPage
import com.example.fragmentsofmemory.fragments.HomePageEntrances
import com.example.fragmentsofmemory.fragments.ReadingFragments
import com.example.fragmentsofmemory.fragments.popUpDrawer
import com.example.fragmentsofmemory.ui.theme.MyTheme
import java.io.File

class Welcome : AppCompatActivity() {

    private val userCardViewModel by viewModels<UserCardViewModel>()

    private val userInfoViewModel by viewModels<UserInfoViewModel>()

    private val dialogViewModel:DialogViewModel by viewModels()
    private val viewModel:UiModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            val context = LocalContext.current
            val file = File(context.getExternalFilesDir(null), "picture.jpg")
            val navController = rememberNavController()


               NavHost(navController, startDestination = "welcome") {
                composable("welcome") { Test( navController) }
                composable("mainpage") {
                    MyTheme(viewModel) {
                        HomePageEntrances(userCardViewModel, userInfoViewModel, file, context)
                        AddingPage(userCardViewModel, file , context)
                        dialogViewModel.PopUpAlertDialog()
                        dialogViewModel.PopUpAlertDialogDrawerItems(userCardViewModel)
                        ReadingFragments(userInfoViewModel, userCardViewModel, file, context)
                        timePicker()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        when(true){
            (viewModel.adding && viewModel.textModify != "") -> dialogViewModel.openDialog = true
            viewModel.adding -> viewModel.endAddPage()
            viewModel.draweringPage -> viewModel.closeDrawerContent()
            viewModel.reading -> viewModel.endReading()
            else -> super.onBackPressed()
        }
    }

    private fun timePicker(){
        if(viewModel.timing) {
            MaterialDialog(this).show {
                datePicker { dialog, date ->
                    viewModel.timeResult = "${date.year}.${date.month + 1}.${date.dayOfMonth}"
                }
            }
        }
        viewModel.timing = false
        if(viewModel.timeResult != "")viewModel.selectedTime = true
    }
}

@Composable
fun Test(navController: NavController) {
    Button(onClick = {
        navController.navigate("mainpage"){
            popUpTo("welcome") { //删除 welcome 界面
                inclusive = true
            }
        }
    }) {
        Text(text = "跳转")
    }
}

