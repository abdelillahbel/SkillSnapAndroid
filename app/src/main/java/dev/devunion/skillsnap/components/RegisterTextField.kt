/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.PreviewLightDark
import dev.devunion.skillsnap.ui.theme.SkillSnapTheme


@Composable
fun RegisterTextField(
    modifier: Modifier = Modifier,
    hint: String,
    isError: Boolean,
    supportText: String,
    value: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    label: String,
    trailingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        isError = isError,
        modifier = modifier,
        label = { Text(text = label) },
        supportingText = {
            if (isError) {
                Text(text = supportText)
            }
        },
        placeholder = {
            Text(text = hint)
        },
        shape = RoundedCornerShape(30f),
        maxLines = 1,
        visualTransformation = visualTransformation,
        trailingIcon = if (trailingIcon == null) null else {
            {
                IconButton(onClick = onTrailingIconClick) {
                    Icon(painter = trailingIcon, contentDescription = "")
                }
            }
        }
    )
}

@PreviewLightDark
@Composable
fun RegisterTextFieldPreview() {
    SkillSnapTheme {
        RegisterTextField(
            hint = "Enter Your email",
            isError = false,
            value = "sss",
            supportText = "",
            label = "sssss"
        ) {

        }
    }
}
