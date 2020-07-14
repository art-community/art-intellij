package io.art.idea.plugin.cached

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.RecursionManager.createGuard
import com.intellij.psi.JavaPsiFacade.getElementFactory
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValueProvider.Result
import com.intellij.psi.util.CachedValueProvider.Result.create
import io.art.idea.plugin.caster.cast

val guard = createGuard(GeneratedCachedValueProvider::class.qualifiedName)

class GeneratedCachedValueProvider<Psi : PsiElement>(private val project: Project, private val psiClass: PsiClass) : CachedValueProvider<List<Psi>> {
    override fun compute() = guard.doPreventingRecursion<Result<List<Psi>>>(psiClass, true, this::computeIntern)

    private fun computeIntern(): Result<List<Psi>> {
        val result = mutableListOf<Psi>()
        val elementFactory = getElementFactory(project)
        when (psiClass.name) {
            "MyService" -> {
                psiClass.findFieldByName("myMethod", false)?.run { return create(result, psiClass) }
                val field = cast<Psi>(elementFactory.createFieldFromText("public static String myMethod = \"myMethod\"", psiClass))
                result.add(field)
            }
        }
        return create(result, psiClass)
    }
}
