/*
 * Copyright (c) 2024. DevUnion Foundation.
 * GitHub: https://github.com/devunionorg
 * All rights reserved.
 *
 * This project was conceptualized and developed by @abdelillahbel.
 * GitHub: https://github.com/abdelillahbel
 */

package dev.devunion.skillsnap.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.devunion.skillsnap.ui.theme.SkillSnapTheme


@Composable
fun RegisterButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(20f),
        enabled = enabled
    ) {
        Text(text = text, fontSize = 19.sp, modifier = Modifier.padding(vertical = 6.dp))
    }
}

@PreviewLightDark
@Composable
fun RegisterButtonPrev() {
    SkillSnapTheme {
        RegisterButton(text = "Sign in", onClick = { /*TODO*/ })
    }
}
