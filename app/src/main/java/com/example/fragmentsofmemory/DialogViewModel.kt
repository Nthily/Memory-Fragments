package com.example.fragmentsofmemory

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fragmentsofmemory.Database.DrawerItems
import com.example.fragmentsofmemory.fragments.userContent
import kotlinx.coroutines.delay
import java.util.Date

class DialogViewModel: ViewModel() {
    var openDialog by  mutableStateOf(false)

    @Composable
    fun PopUpAlertDialog(viewModel: UiModel) {

        if (openDialog) {

            if(viewModel.textModify.isBlank()){     // 内容为空或只包含不可见字符（空格、换行等）
                openDialog = false
                viewModel.adding = false
                viewModel.maining = true
            }

            else {
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        openDialog = false
                    },
                    title = {
                        Text(text = "还有没写完的东西呐,你确定要退出🐎")
                    },
                    text = {
                        Text(text = "埃拉我i耨爱三到四阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿阿斯顿u暗送不低啊建瓯市第???")
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // TODO 检测是否还有文字
                                openDialog = false
                                viewModel.timeResult = ""
                                viewModel.textModify = ""
                                viewModel.adding = false
                                viewModel.maining = true
                            }
                        ) {
                            Text("确定")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog = false
                            }
                        ) {
                            Text("留着继续写")
                        }
                    }
                )
            }
        }
    }



/*
    @Composable
    fun ConfirmAlertDialog(userCardViewModel: UserCardViewModel) {
        val viewModel: UiModel = viewModel()
        if (finishDialog) {
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    finishDialog = false
                },
                title = {
                    Text(text = "确定将添加到碎片中吗")
                },
                text = {
                    Text(text = "好的不好好的不要,好的,添加吧")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            userCardViewModel.AddDatabase("nmsl", userContent.value)
                            finishDialog = false
                            viewModel.adding = false
                        }
                    ) {
                        Text("确定")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            finishDialog = false
                        }
                    ) {
                        Text("还是算了吧")
                    }
                }
            )
        }
    }*/


    @ExperimentalComposeUiApi
    @Composable
    fun PopUpAlertDialogDrawerItems(viewModel: UiModel, userCardViewModel: UserCardViewModel) {
        val focus = FocusRequester()
        val keyboard = LocalSoftwareKeyboardController.current

        if(viewModel.addNewCategory || viewModel.editingCategory) {

            val categoryName0 by remember { mutableStateOf(viewModel.categoryName) }    // 正在编辑的分类的原名称
            var categoryName by remember { mutableStateOf(viewModel.categoryName) }

            val error1 = categoryName.isBlank()    // 分类名称为空错误
            val error2 = userCardViewModel.drawer.value?.any {    // 分类名称已存在错误
                val con = it.drawerItems.trimEnd() == categoryName.trimEnd()
                (viewModel.addNewCategory && con)
                        || (viewModel.editingCategory && con && it.uid != viewModel.editingCategoryUid)
            } ?: true

            AlertDialog(

                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    viewModel.addNewCategory = false
                    viewModel.editingCategory = false
                },
                title = {
                    Text(if(viewModel.editingCategory) "修改分类 \"${categoryName0}\" 的名字啦~" else "添加新的分类~")
                },
                text = {

                   Column(modifier = Modifier.padding(top = 10.dp)) {



                       Row(){
                           // TODO: 下个版本再添加选择分类图标功能
                           /*Surface(
                               shape = CircleShape,
                               color = (Color(208, 207, 209)),
                               modifier = Modifier
                                   .size(20.dp)
                                   .clickable {
                                   }
                                   .align(Alignment.CenterVertically)
                           ) {

                           }*/

                           TextField(value = categoryName, onValueChange = {
                               categoryName = it.replace("\n", "")
                           },
                               isError = error1 || error2,
                               modifier = Modifier.focusRequester(focus),
                               colors = TextFieldDefaults.textFieldColors(
                                   backgroundColor = Color(255, 255, 255, 1)),
                               textStyle = LocalTextStyle.current.copy(fontWeight = FontWeight.W900),
                               singleLine = true,
                               maxLines = 1
                           )
                           LaunchedEffect(viewModel.addNewCategory) {
                               if(viewModel.addNewCategory) {
                                   delay(300)
                                   focus.requestFocus()
                                   keyboard?.showSoftwareKeyboard()
                               }
                           }
                           LaunchedEffect(viewModel.editingCategory) {
                               if(viewModel.editingCategory) {
                                   delay(300)
                                   focus.requestFocus()
                                   keyboard?.showSoftwareKeyboard()
                               }
                           }

                       }
                       if(error2) {
                           Text("* 该分类已存在", modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.body2, color = Color(0xFFD53030))
                       }
                   }

                    /*LaunchedEffect(viewModel.editingCategory) {
                        if(viewModel.editingCategory) {
                            focus.requestFocus()
                            delay(500)
                            keyboard?.showSoftwareKeyboard()
                        }
                    }*/
                },
                confirmButton = {
                    TextButton(
                        enabled = !(error1 || error2),
                        onClick = {
                            if(viewModel.addNewCategory) {
                                userCardViewModel.addCategoryDataBase(categoryName)
                                viewModel.addNewCategory = false
                                viewModel.categoryName = ""
                            }

                            if(viewModel.editingCategory){
                                userCardViewModel.updateCategoryDataBaseName(viewModel.editingCategoryUid, categoryName)
                                viewModel.editingCategory = false
                                viewModel.categoryName = ""
                            }
                        }
                    ) {
                        Text("确定")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.addNewCategory = false
                            viewModel.categoryName = ""
                            viewModel.editingCategory = false
                        }
                    ) {
                        Text("取消")
                    }
                },
            )
        }

    }


    @Composable
    fun PopUpConfirmDeleteItem(viewModel: UiModel, userCardViewModel: UserCardViewModel) {

        if (viewModel.requestDeleteCard) {

            AlertDialog(
                onDismissRequest = {
                    viewModel.requestDeleteCard = false
                },
                title = {
                    Text(text = "真的要删除这片记忆嘛")
                },
                text = {
                    Text(text = "阿巴阿巴阿巴阿巴再考虑考虑啦")
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteCard(userCardViewModel)
                            viewModel.requestDeleteCard = false
                        }
                    ) {
                        Text("确定")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            viewModel.requestDeleteCard = false
                        }
                    ) {
                        Text("取消")
                    }
                }
            )
        }
    }
}