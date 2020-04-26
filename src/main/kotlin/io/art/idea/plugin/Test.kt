package io.art.idea.plugin

import com.intellij.bootRuntime.ui.dialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.ui.components.Label
import com.intellij.ui.layout.panel
import ru.art.generator.mapper.Generator.performGeneration
import java.awt.Dialog.ModalityType.APPLICATION_MODAL
import java.awt.Dimension
import javax.swing.JTextField
import javax.swing.SwingConstants.CENTER

class Test : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val packageNameLabel = Label("Absolute path to compiled 'model' folder")
        val absolutePathTextField = JTextField()

        packageNameLabel.horizontalAlignment = CENTER

        val panel = panel {
            row {
                packageNameLabel(growX)
            }
            row {
                absolutePathTextField()
            }
            row {
                button("Generate") {
                    performGeneration(absolutePathTextField.text.substringBeforeLast("/"), "model", "mapping")
                }
            }
        }

        panel.apply {
            minimumSize = Dimension(300, 300)
            maximumSize = Dimension(400, 400)
            preferredSize = Dimension(300, 300)
            size = Dimension(300, 300)
        }

        val dialog = dialog(title = "Application Generator", modalityType = APPLICATION_MODAL, centerPanel = panel)
        dialog.minimumSize = Dimension(300, 300)
        dialog.isModal = true
        dialog.setLocationRelativeTo(null)
    }
}
