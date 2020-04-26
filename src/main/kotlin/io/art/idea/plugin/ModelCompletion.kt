package io.art.idea.plugin

import com.intellij.codeInsight.TailType
import com.intellij.codeInsight.TailType.*
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType.BASIC
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementBuilder.create
import com.intellij.codeInsight.lookup.LookupElementBuilder.createWithIcon
import com.intellij.icons.AllIcons
import com.intellij.icons.AllIcons.Nodes.Field
import com.intellij.lang.java.JavaLanguage
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.JavaPsiFacade.getInstance
import com.intellij.psi.PsiAnnotation
import com.intellij.psi.PsiAnnotation.DEFAULT_REFERENCED_METHOD_NAME
import com.intellij.psi.PsiAnnotationParameterList
import com.intellij.psi.PsiIdentifier
import com.intellij.psi.PsiNamedElement
import com.intellij.psi.impl.PsiImplUtil
import com.intellij.psi.impl.source.tree.java.PsiReferenceExpressionImpl
import com.intellij.psi.search.GlobalSearchScope.allScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiTreeUtil.findElementOfClassAtOffset
import com.intellij.util.ProcessingContext

class ModelCompletion : CompletionContributor() {
    init {
        extend(BASIC, psiElement(PsiIdentifier::class.java).withLanguage(JavaLanguage.INSTANCE), object : CompletionProvider<CompletionParameters>() {
            override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, result: CompletionResultSet) {
                val element = parameters.position.prevSibling?.prevSibling?.prevSibling as? PsiReferenceExpressionImpl
                element ?: return
                getInstance(element.project)
                        .findClass(element.qualifiedName, allScope(element.project))
                        ?.hasAnnotation("ru.art.generator.compiler.annotation.Model")
                        ?.let {
                            result.addElement(create("toModel").withIcon(Field).withTailText("ART ValueToModel mapper").bold())
                            result.addElement(create("fromModel").withIcon(Field).withTailText("ART ValueFromModel mapper").bold())
                        }

            }
        }
        )
    }
}